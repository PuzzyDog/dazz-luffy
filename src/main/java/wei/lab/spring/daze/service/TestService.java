package wei.lab.spring.daze.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * created by weixuecai on 2018/9/25
 */
@Service
public class TestService {

    @Value("${db.driverName}")
    private String tmpVar;

    @Value("${db.maxTotal}")
    private int intVar;

    @Value("#{'${list.source}'.split(',')}")
    private List<String> strList;

    @Value("#{'${plain.list}'.split(',')}")
    private List<String> plainStrList;


    public String getTmpVar() {
        return tmpVar + ", " + strList.get(0) + ", " + plainStrList.get(0);
    }
}
