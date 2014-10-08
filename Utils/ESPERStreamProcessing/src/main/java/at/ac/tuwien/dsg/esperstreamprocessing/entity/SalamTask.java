package at.ac.tuwien.dsg.esperstreamprocessing.entity;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Task", description = "Task representation")
public class SalamTask {
    
    @ApiModelProperty(value = "Task's id", required = true)
    private Integer id;

    @ApiModelProperty(value = "Task's name", required = true)
    private String name;

    @ApiModelProperty(value = "Task's content", required = true)
    private String content;

    @ApiModelProperty(value = "Task's tag, e.g., a category", required = true)
    private String tag; 

    @ApiModelProperty(value = "Task's severity", required = true)
    private SeverityLevel severity;

    public enum SeverityLevel {
        NOTICE, WARNING, CRITICAL, ALERT, EMERGENCY
    }

    public SalamTask() {
    }

    public SalamTask(Integer id, String name, String content,
            String tag, SeverityLevel severity) {
        super();
        this.id = id;
        this.name = name;
        this.content = content;
        this.tag = tag;
        this.severity = severity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public SeverityLevel getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityLevel severity) {
        this.severity = severity;
    }

}
