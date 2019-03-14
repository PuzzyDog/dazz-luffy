package wei.lab.spring.daze.db.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

/**
 * created by weixuecai on 2018/6/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("PersonAlterLog")
public class TbPersonAlterLog {
    public long logId;
    public String logData;
    public long userId;
    public long createTime;
}
