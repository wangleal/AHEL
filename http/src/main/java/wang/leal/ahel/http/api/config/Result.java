package wang.leal.ahel.http.api.config;

public class Result {
    private String codeField;
    private String messageField;
    private String dataField;
    private int successCode;

    public Result(String codeField,String messageField,String dataField,int successCode){
        this.codeField = codeField;
        this.messageField = messageField;
        this.dataField = dataField;
        this.successCode = successCode;
    }

    public String codeField() {
        return codeField;
    }

    public String messageField() {
        return messageField;
    }

    public String dataField() {
        return dataField;
    }

    public int successCode(){
        return successCode;
    }
}
