package wei.lab.spring.daze.service;

import wei.lab.spring.daze.db.dao.IPersonAlterLogDAO;
import wei.lab.spring.daze.db.dao.IPersonInfoDAO;
import wei.lab.spring.daze.db.entity.TbPersonAlterLog;
import wei.lab.spring.daze.db.entity.TbPersonInfo;
import wei.lab.spring.daze.db.ext.DbSelector;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by weixuecai on 2018/6/12
 */
@Service
public class DataAccessService {
    @Autowired
    IPersonAlterLogDAO personAlterLogDAO;

    @Autowired
    IPersonInfoDAO personInfoDAO;

    @DbSelector(dbKey = "one|three")
    public List<TbPersonInfo> selectAllPersonInfo() {
        List<TbPersonInfo> personInfos = personInfoDAO.selectAll();
        return personInfos;
    }

//    @Cacheable(value = "cache5Secs", key = "'selectPersonById' + #id")
    @DbSelector(dbKey = "one"/*dependParam = "#id", dataType = 0*/)
    public TbPersonInfo selectPersonById(@Param("id") long id) {
        TbPersonInfo personInfo = personInfoDAO.selectById(id);
        return personInfo;
    }

    @Cacheable("cache5Secs")
    public TbPersonInfo hello(long value) {
        return null;
    }

    @DbSelector(dependParam = "#person.id", dataType = 0)
    @Transactional(rollbackFor = Exception.class)
    public int insertPersonInfo(@Param("person") TbPersonInfo personInfo) {
        int effectedRows = personInfoDAO.insert(personInfo);
        throw new RuntimeException("My Exception for rollback.");
//        return effectedRows;
    }

    //
    @DbSelector(dbKey = "three")
    public List<TbPersonAlterLog> selectAllAlterLogs() {
        return personAlterLogDAO.selectAll();
    }

    @DbSelector(dependParam = "#log.logId", dataType = 1)
    public int insertAlterLog(TbPersonAlterLog alterLog) {
        return personAlterLogDAO.insert(alterLog);
    }


    @DbSelector(dbKey = "one")
    public List<TbPersonInfo> selectPersonByIdRange(int startId, int endId) {
        return personInfoDAO.selectByIdRange(startId, endId);
    }
}
