package com.hal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertTrue;
import com.hal.entity.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;


public class AppTest {

    private final ParentTabDTO ptd;
    private final String jsonRep;
    private final String jsonEnt;
    
    public AppTest(){
        ptd = new ParentTabDTO(1,"parent_1");
        ChildTabDTO ctpd1 = new ChildTabDTO(1);
        ctpd1.setOe(new OtherEntity(11));
        ChildTabDTO ctpd2 = new ChildTabDTO(2);
        ctpd2.setOe(new OtherEntity(22));
        List<ChildTabDTO> ct = new ArrayList<>();
        ct.add(ctpd1);
        ct.add(ctpd2);
        ptd.setCt(ct);
        ptd.setSomeDate(LocalDate.parse("20-02-2015",DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        
        jsonRep = "{\n" +
"  \"parent_date\": \"20-02-2015\",\n" +
"  \"parent_id\": 1,\n" +
"  \"parent_value\": \"parent_asdggg\",\n" +
"  \"_links\": {\n" +
"    \"curies\": [\n" +
"      {\n" +
"        \"href\": \"link2/{rel}\",\n" +
"        \"templated\": true,\n" +
"        \"name\": \"b\"\n" +
"      }\n" +
"    ],\n" +
"    \"self\": {\n" +
"      \"href\": \"self uri\"\n" +
"    }\n" +
"  },\n" +
"  \"_embedded\": {\n" +
"    \"b:child\": [\n" +
"      {\n" +
"        \"n\": 2,\n" +
"        \"_links\": {\n" +
"          \"curies\": [\n" +
"            {\n" +
"              \"href\": \"link1\",\n" +
"              \"name\": \"a\"\n" +
"            },\n" +
"            {\n" +
"              \"href\": \"link1/{rel}\",\n" +
"              \"templated\": true,\n" +
"              \"name\": \"b1\"\n" +
"            }\n" +
"          ],\n" +
"          \"self\": {\n" +
"            \"href\": \"http://localhost:8080/mavenproject3/serv/child/id/2\"\n" +
"          }\n" +
"        }\n" +
"      },\n" +
"      {\n" +
"        \"n\": 1,\n" +
"        \"_links\": {\n" +
"          \"curies\": [\n" +
"            {\n" +
"              \"href\": \"link1\",\n" +
"              \"name\": \"a\"\n" +
"            },\n" +
"            {\n" +
"              \"href\": \"link1/{rel}\",\n" +
"              \"templated\": true,\n" +
"              \"name\": \"b1\"\n" +
"            }\n" +
"          ],\n" +
"          \"self\": {\n" +
"            \"href\": \"http://localhost:8080/mavenproject3/serv/child/id/1\"\n" +
"          }\n" +
"        }\n" +
"      }\n" +
"    ]\n" +
"  }\n" +
"}";
        jsonEnt = "{\n" +
"  \"parent_date\": \"20-02-2015\",\n" +
"  \"parent_id\": 1,\n" +
"  \"parent_value\": \"parent_asdggg\",\n" +
"  \"_links\": {\n" +
"    \"curies\": [\n" +
"      {\n" +
"        \"href\": \"link2/{rel}\",\n" +
"        \"templated\": true,\n" +
"        \"name\": \"b\"\n" +
"      }\n" +
"    ],\n" +
"    \"self\": {\n" +
"      \"href\": \"self uri\"\n" +
"    }\n" +
"  },\n" +
"  \"_embedded\": {\n" +
"    \"child\": [\n" +
"      {\n" +
"        \"n\": 3,\n" +
"        \"_links\": {\n" +
"          \"curies\": [\n" +
"            {\n" +
"              \"href\": \"link1\",\n" +
"              \"name\": \"a\"\n" +
"            },\n" +
"            {\n" +
"              \"href\": \"link1/{rel}\",\n" +
"              \"templated\": true,\n" +
"              \"name\": \"b1\"\n" +
"            }\n" +
"          ],\n" +
"          \"self\": {\n" +
"            \"href\": \"http://localhost:8080/mavenproject3/serv/child/id/3\"\n" +
"          }\n" +
"        },\n" +
"	\"_embedded\": {\n" +
"    	\"childchild\":\n" +
"      	{\n" +
"          \"n\": 3,\n" +
"          \"_links\": {\n" +
"          	\"curies\": [\n" +
"            	{\n" +
"              	\"href\": \"link1\",\n" +
"              	\"name\": \"a1\"\n" +
"            	},\n" +
"            	{\n" +
"              	\"href\": \"link1/{rel}\",\n" +
"              	\"templated\": true,\n" +
"              	\"name\": \"b11\"\n" +
"            	}\n" +
"          	],\n" +
"          	\"self\": {\n" +
"            		\"href\": \"http://localhost:8080/mavenproject3/serv/childchild/id/3\"\n" +
"          	}\n" +
"        }\n" +
"      }\n" +
"    }\n" +
"      },\n" +
"      {\n" +
"        \"n\": 2,\n" +
"        \"_links\": {\n" +
"          \"curies\": [\n" +
"            {\n" +
"              \"href\": \"link1\",\n" +
"              \"name\": \"a\"\n" +
"            },\n" +
"            {\n" +
"              \"href\": \"link1/{rel}\",\n" +
"              \"templated\": true,\n" +
"              \"name\": \"b1\"\n" +
"            }\n" +
"          ],\n" +
"          \"self\": {\n" +
"            \"href\": \"http://localhost:8080/mavenproject3/serv/child/id/2\"\n" +
"          }\n" +
"        }\n" +
"      },\n" +
"      {\n" +
"        \"n\": 1,\n" +
"        \"_links\": {\n" +
"          \"curies\": [\n" +
"            {\n" +
"              \"href\": \"link1\",\n" +
"              \"name\": \"a\"\n" +
"            },\n" +
"            {\n" +
"              \"href\": \"link1/{rel}\",\n" +
"              \"templated\": true,\n" +
"              \"name\": \"b1\"\n" +
"            }\n" +
"          ],\n" +
"          \"self\": {\n" +
"            \"href\": \"http://localhost:8080/mavenproject3/serv/child/id/1\"\n" +
"          }\n" +
"        }\n" +
"      }\n" +
"    ]\n" +
"  }\n" +
"}";
    }

    @Test
    public void testProps() {
        Representation rpr = new Representation.RepBuilder(ptd, "parent self uri "+ptd.getN())
                .build();
        
        JsonNode json = Utils.getMapper().valueToTree(rpr);
        System.out.println(json.toPrettyString());
        assertTrue(json.has("parent_id")&&json.has("parent_value")&&json.has("parent_date"));
        assertArrayEquals(new Object[]{json.get("parent_id"), json.get("parent_value"), json.get("parent_date")}
                          ,new Object[]{new IntNode(1), new TextNode("parent_1"), new TextNode("20-02-2015")});
    }
    
    @Test
    public void testLinks() {
        Set<Representation.RepBuilder> st2 = new HashSet<>();
        ptd.getCt().forEach(
                ctp->{       
                     st2.add(new Representation.RepBuilder(ctp, "child self uri "+ctp.getN())
                                );        
                     }
        );

        Representation rpr = new Representation.RepBuilder(ptd, "parent self uri "+ptd.getN())
                .addEmbedded("child", st2)
                .build();
        
        JsonNode json = Utils.getMapper().valueToTree(rpr);
        assertFalse(json.get("_links").isEmpty());
    }
    
    @Test
    public void testEmbedded() {
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

        JsonNode json = Utils.getMapper().valueToTree(rpr);
        assertTrue(json.get("_embedded").has("child"));
        assertTrue(json.get("_embedded").get("child").get(0).get("_embedded").has("oe"));
        assertTrue(json.get("_embedded").get("child").get(0).get("_embedded").get("oe").get("n").asText().equals("11"));
    }
    
    @Test
    public void testCurieResolve() {
        Set<Representation.RepBuilder> st2 = new HashSet<>();
        ptd.getCt().forEach(
                ctp->{       
                     st2.add(new Representation.RepBuilder(ctp, "child self uri "+ctp.getN())
                                );        
                     }
        );

        Representation rpr = new Representation.RepBuilder(ptd, "parent self uri "+ptd.getN())
                .addCurie("b", "link2/{rel}")
                .addEmbedded("link2/child", st2)
                .build();
        JsonNode json = Utils.getMapper().valueToTree(rpr);
        assertTrue(json.get("_embedded").has("b:child"));
    }
    
    @Test
    public void testDuplicateCurieRemove() {
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
        JsonNode json = Utils.getMapper().valueToTree(rpr);
        assertFalse(json.get("_embedded")
                        .get("b:child")
                        .get(0)
                        .get("_links")
                        .get("curies")
                        .has(1)
        );
    }
    
    @Test
    public void testHalParserRepresentationResloveLink() throws JsonProcessingException {
        
        JsonNode json = Utils.getMapper().readTree(jsonRep);
        Representation rep = Utils.getParser().createRepObject(json);

        assertTrue(rep.getEmbedded().containsKey("link2/child"));

    }
    
    @Test
    public void testHalParserRepresentationEmbedded() throws JsonProcessingException {
        
        JsonNode json = Utils.getMapper().readTree(jsonRep);
        Representation rep = Utils.getParser().createRepObject(json);

        JavaType type = Utils.getMapper().getTypeFactory().constructParametricType(List.class, ChildTabDTO.class);
        ParentTabDTO ptd2 = new ParentTabDTO();
        ptd2.setCt(Utils.getMapper().convertValue(rep.getEmbedded().get("link2/child"), type));
        ChildTabDTO ctpd1 = new ChildTabDTO(1);
        
        assertTrue(((Collection)rep.getEmbedded().get("link2/child")).size()==2);
        assertTrue(ptd2.getCt().size()==2);
        assertTrue(ptd2.getCt().contains(ctpd1));
  
    }
    
    
    @Test
    public void testHalParserEntityProps() throws JsonProcessingException {
        
        JsonNode json = Utils.getMapper().readTree(jsonEnt);
        ParentTabDTO ptd2 = Utils.getParser().createEntityObject(json, ParentTabDTO.class);
        ChildTabDTO ctpd1 = new ChildTabDTO(1);
        OtherEntity oe1 = new OtherEntity(3);
        
        assertTrue(ptd2.getV().equals("parent_asdggg"));
        assertTrue(ptd2.getN().equals(1));
        assertTrue(ptd2.getSomeDate().equals(LocalDate.parse("20-02-2015",DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        assertTrue(ptd2.getCt().contains(ctpd1));
        assertTrue(ptd2.getCt().get(0).getOe().equals(oe1));
        
    }

}
