package wei.lab.spring.daze.db.dao;

import wei.lab.spring.daze.db.entity.TbPersonAlterLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by weixuecai on 2018/6/12
 */
@Repository
public interface IPersonAlterLogDAO {
    /**
     * 查询所有日志记录
     * @return
     */
    List<TbPersonAlterLog> selectAll();

    /**
     * 插入一条记录
     * @param personAlterLog
     * @return
     */
    int insert(TbPersonAlterLog personAlterLog);
}
