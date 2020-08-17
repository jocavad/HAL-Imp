# HAL-b

Small library for serializing Java objects to [HAL JSON](https://tools.ietf.org/html/draft-kelly-json-hal) format and parse back to Java objects.
Serialization and parsing, that both rely heavily on Jackson library, is done through `Representation` object.
Jackson library version should be at least 2.10.0.

## 1 - Serialize

### 1.1 - POJO with Jackson annotations
`Embedded` objects (like `List<ChildTabDTO> ct`, or `OtherEntity oe`) should be accessed only during deseralization

```java
@JsonPropertyOrder({"parent_id", "parent_value"}) //property order can be changed here
public class ParentTabDTO implements java.io.Serializable {

    @JsonProperty(value = "parent_id")
    private Integer n;
    @JsonProperty(value = "parent_value")
    private String v;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "child") //embedded
    private List<ChildTabDTO> ct = new ArrayList<>(0);
    @JsonDeserialize(using = LocalDateDeserializer.class)  
    @JsonSerialize(using = LocalDateSerializer.class)  
    @JsonProperty(value = "parent_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate someDate;

//getters/setters
}

public class ChildTabDTO implements java.io.Serializable {

    private Integer n;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "childchild") //embedded
    private OtherEntity oe;
 
//getters/setters    
}

public class OtherEntity implements java.io.Serializable {

    private Integer n;
}
```

### 1.2 - Data

```java
ParentTabDTO ptd = new ParentTabDTO(1,"parent_1");
ChildTabDTO ctpd1 = new ChildTabDTO(1);
ctpd1.setOe(new OtherEntity(11));
ChildTabDTO ctpd2 = new ChildTabDTO(2);
ctpd2.setOe(new OtherEntity(22));
List<ChildTabDTO> ct = new ArrayList<>();
ct.add(ctpd1);
ct.add(ctpd2);
ptd.setCt(ct);
ptd.setSomeDate(LocalDate.parse("2015-02-20",DateTimeFormatter.ofPattern("dd-MM-yyyy")));
```

### 1.3 - Properties

`Representation` object

``` java
Representation rpr = new Representation.RepBuilder(ptd, "parent self uri "+ptd.getN())
                                       .build();
```

will serialize to

```json
{
  "parent_id" : 1,
  "parent_value" : "parent_1",
  "parent_date" : "20-02-2015",
  "_links" : {
    "self" : {
      "href" : "parent self uri 1"
    }
  }
}
```

### 1.4 - Embedded

Embedded objects can only be added as `RepBuilder` or `Collection<RepBuilder>`

```java
List<Representation.RepBuilder> ls = new ArrayList<>();
        ptd.getCt().forEach(
                ctp->{       
                     ls.add(new Representation.RepBuilder(ctp, "child self uri "+ctp.getN())
                                               .addEmbedded("oe", new Representation.RepBuilder(ctp.getOe(), "oe self uri "+ctp.getN()))
                                );
                     }
        );
ls.sort((Representation.RepBuilder o1, Representation.RepBuilder o2) -> ((Integer)o1.getProp().get("n")).compareTo((Integer)o2.getProp().get("n")));

Representation rpr = new Representation.RepBuilder(ptd, "parent self uri "+ptd.getN())
                                       .addEmbedded("child", ls)
                                       .build();
```

and it will serialize to

```json
{
  "parent_id" : 1,
  "parent_value" : "parent_1",
  "parent_date" : "20-02-2015",
  "_links" : {
    "self" : {
      "href" : "parent self uri 1"
    }
  },
  "_embedded" : {
    "child" : [ {
      "n" : 1,
      "_links" : {
        "self" : {
          "href" : "child self uri 1"
        }
      },
      "_embedded" : {
        "oe" : {
          "n" : 11,
          "_links" : {
            "self" : {
              "href" : "oe self uri 1"
            }
          }
        }
      }
    } ]
  }
}
```

### 1.5 - Curies

Curies are also possible

```java
Set<Representation.RepBuilder> st2 = new HashSet<>();
        ptd.getCt().forEach(
                ctp->{       
                     st2.add(new Representation.RepBuilder(ctp, "child self uri "+ctp.getN())
                                               .addCurie("b", "link1/{rel}")
                                               .addCurie("b", "link2/{rel}")
                             );        
                     }
        );

Representation rpr = new Representation.RepBuilder(ptd, "parent self uri "+ptd.getN())
                                       .addCurie("b", "link2/{rel}")
                                       .addEmbedded("link2/child", st2)
                                       .build();
```

and it will serialize along with duplicate curie removal

```json
{
  "parent_id" : 1,
  "parent_value" : "parent_1",
  "parent_date" : "20-02-2015",
  "_links" : {
    "curies" : [ {
      "href" : "link2/{rel}",
      "templated" : true,
      "name" : "b"
    } ],
    "self" : {
      "href" : "parent self uri 1"
    }
  },
  "_embedded" : {
    "b:child" : [ {
      "n" : 1,
      "_links" : {
        "curies" : [ {
          "href" : "link1/{rel}",
          "templated" : true,
          "name" : "b"
        } ],
        "self" : {
          "href" : "child self uri 1"
        }
      }
    } ]
  }
}
```

`Links` with same `href` and `name` are considered duplicate according to `hashcode/equals`. This can be changed in `Links` class.

## 2 - Parse

Parsing relies on Jacksons `@JsonProperty()` or field names.

Parsing To entity

```java
ParentTabDTO ptd2 = Utils.getParser().createEntityObject(json, ParentTabDTO.class);
```

or to `Representation` object if relations differs from class's field names

```java
Representation rep = Utils.getParser().createRepObject(json);
JavaType type = Utils.getMapper().getTypeFactory().constructParametricType(List.class, ChildTabDTO.class);
ParentTabDTO ptd2 = new ParentTabDTO();
ptd2.setCt(Utils.getMapper().convertValue(rep.getEmbedded().get("link2/child"), type));
```
