package wei.lab.spring.daze.db.dao;

import wei.lab.spring.daze.db.entity.TbPersonInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by weixuecai on 2018/6/12
 */
@Repository
public interface IPersonInfoDAO {
    /**
     * 查询所有的个人信息
     * @return
     */
    List<TbPersonInfo> selectAll();

    /**
     * 查询指定个人信息
     * @param id
     * @return
     */
    TbPersonInfo selectById(@Param("id") long id);

    /**
     * 插入一条个人信息
     * @param personInfo
     * @return
     */
    int insert(TbPersonInfo personInfo);

    /**
     * 根据id查询
     * @param startId
     * @param endId
     * @return
     */
    List<TbPersonInfo> selectByIdRange(@Param(value = "startId") int startId, @Param(value = "endId") int endId);
}
