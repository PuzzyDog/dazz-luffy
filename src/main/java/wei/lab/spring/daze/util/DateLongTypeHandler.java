package wei.lab.spring.daze.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.*;

/**
 * Created by weixuecai on 2017/11/30.
 */
@MappedTypes(long.class)
@MappedJdbcTypes(JdbcType.TIMESTAMP)
public class DateLongTypeHandler extends BaseTypeHandler<Long> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Long parameter, JdbcType jdbcType) throws SQLException {
        long input = parameter;
        if(input <0) {
            input = 0;
        }

        ps.setTimestamp(i, new Timestamp(input));
    }

    @Override
    public Long getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp stamp = rs.getTimestamp(columnName);
        if(stamp ==null)
            return 0L;

        return stamp.getTime();
    }

    @Override
    public Long getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp stamp = rs.getTimestamp(columnIndex);
        if(stamp ==null)
            return 0L;

        return stamp.getTime();
    }

    @Override
    public Long getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp stamp = cs.getTimestamp(columnIndex);
        if(stamp ==null)
            return 0L;

        return stamp.getTime();
    }
}
