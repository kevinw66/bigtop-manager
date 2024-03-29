package org.apache.bigtop.manager.server.stack.pojo;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class OSSpecificModel {

    @XmlElementWrapper(name = "operating-systems")
    @XmlElements(@XmlElement(name = "os"))
    private List<String> os;

    @XmlElementWrapper(name = "architectures")
    @XmlElements(@XmlElement(name = "arch"))
    private List<String> arch;

    @XmlElementWrapper(name = "packages")
    @XmlElements(@XmlElement(name = "package"))
    private List<String> packages;
}
