package wei.lab.spring.daze.entity;

/**
 * created by weixuecai on 2019/3/16
 */
public class TestEntity {
    private long field1;
    private String field2;

    public TestEntity() {
        System.out.println("TestEntity is created.");
    }

    public long getField1() {
        return field1;
    }

    public void setField1(long field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }
}
