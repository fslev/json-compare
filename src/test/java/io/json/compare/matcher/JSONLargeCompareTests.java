package io.json.compare.matcher;

import io.json.compare.JSONCompare;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JSONLargeCompareTests {

    @Test
    public void compareBigJsons() {
        String expected = """
                [
                  {
                    "_id": "5b1fc6b3ffc466654b645469",
                    "index": 0,
                    "guid": "f0b70f36-549d-4660-ba96-3fb6d4be5517",
                    "isActive": true,
                    "balance": "\\\\Q$1,029.68\\\\E",
                    "picture": "http://placehold.it/32x32",
                    "age": 29,
                    "eyeColor": "blue",
                    "name": "Lori Stephenson",
                    "gender": "female",
                    "company": "ZILLACON",
                    "email": "loristephenson@zillacon.com",
                    "phone": "\\\\Q+1 (896) 542-2167\\\\E",
                    "address": "830 Claver Place, Juarez, New Mexico, 8301",
                    "about": "Irure esse ad ullamco velit pariatur excepteur eiusmod ut commodo. In incididunt sunt sit et occaecat culpa aliqua sunt aute fugiat nostrud pariatur non dolor. Sint ad ullamco aliqua reprehenderit et irure laborum nostrud voluptate elit dolore in ad. Enim nulla in incididunt sunt consequat ullamco amet ipsum magna officia dolor.\\r\\n",
                    "registered": "2015-05-04T08:36:29 -03:00",
                    "latitude": -16.071608,
                    "longitude": -59.93564,
                    "tags": [
                      "do",
                      "aliquip",
                      "laborum",
                      "magna",
                      "ut",
                      "pariatur",
                      "ut"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Carroll Strickland"
                      },
                      {
                        "id": 1,
                        "name": "Esperanza Weiss"
                      },
                      {
                        "id": 2,
                        "name": "Kinney Hudson"
                      }
                    ],
                    "greeting": "Hello, Lori Stephenson! You have 9 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b346caf914332726b9",
                    "index": 1,
                    "guid": "d5656dec-6db7-401b-98eb-ea4fafc7cdca",
                    "isActive": false,
                    "balance": "\\\\Q$1,907.04\\\\E",
                    "picture": "http://placehold.it/32x32",
                    "age": 36,
                    "eyeColor": "brown",
                    "name": "Dorothy Pate",
                    "gender": "female",
                    "company": "PYRAMIA",
                    "email": "dorothypate@pyramia.com",
                    "phone": "\\\\Q+1 (872) 483-2665\\\\E",
                    "address": "615 Meeker Avenue, Hiwasse, Missouri, 8757",
                    "about": "Officia est in Lorem aliqua ad velit. Voluptate velit pariatur ex eiusmod. Id nulla quis veniam qui consequat ad ea non esse nisi mollit labore ea laboris. Aliqua sit deserunt duis non mollit consequat proident veniam. Commodo cillum proident irure et deserunt veniam sunt consequat irure velit ea incididunt culpa labore. Excepteur exercitation eiusmod laboris minim Lorem.\\r\\n",
                    "registered": "2014-04-03T07:02:38 -03:00",
                    "latitude": 24.589299,
                    "longitude": 163.036427,
                    "tags": [
                      "ut",
                      "sunt",
                      "tempor",
                      "commodo",
                      "nulla",
                      "cupidatat",
                      "veniam"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Louella Maxwell"
                      },
                      {
                        "id": 1,
                        "name": "Yang Holland"
                      },
                      {
                        "id": 2,
                        "name": "Bobbi Delgado"
                      }
                    ],
                    "greeting": "Hello, Dorothy Pate! You have 7 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b3a746b2ffaf7fc480",
                    "index": 2,
                    "guid": "a20dbd35-4210-47ad-b8eb-354af186851f",
                    "isActive": true,
                    "balance": "\\\\Q$2,733.81\\\\E",
                    "picture": "http://placehold.it/32x32",
                    "age": 24,
                    "eyeColor": "brown",
                    "name": "Salazar Ryan",
                    "gender": "male",
                    "company": "ENOMEN",
                    "email": "salazarryan@enomen.com",
                    "phone": "\\\\Q+1 (833) 504-2359\\\\E",
                    "address": "902 Paerdegat Avenue, Madaket, Northern Mariana Islands, 8664",
                    "about": "Nostrud est do id ut. Elit laboris consequat quis ea id Lorem consectetur sit. Sit aliquip incididunt ipsum nisi excepteur cupidatat aliquip. Enim aliquip culpa esse ad in qui quis enim dolor laborum sit. Incididunt pariatur cupidatat culpa anim sunt aute enim irure aliquip. Eu veniam irure enim magna. Amet dolore cupidatat enim in incididunt non amet irure magna.\\r\\n",
                    "registered": "2015-05-07T09:04:10 -03:00",
                    "latitude": -6.248778,
                    "longitude": 50.080523,
                    "tags": [
                      "labore",
                      "consequat",
                      "magna",
                      "officia",
                      "exercitation",
                      "exercitation",
                      "dolore"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Mercado Valencia"
                      },
                      {
                        "id": 1,
                        "name": "Jordan Finley"
                      },
                      {
                        "id": 2,
                        "name": "Malone Chapman"
                      }
                    ],
                    "greeting": "Hello, Salazar Ryan! You have 3 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b3b7ac57348b1664c7",
                    "index": 3,
                    "guid": "739ee20a-7892-4fb6-8c82-ec8f0ea60a21",
                    "isActive": true,
                    "balance": "\\\\Q$1,272.76\\\\E",
                    "picture": "http://placehold.it/32x32",
                    "age": 27,
                    "eyeColor": "green",
                    "name": "Aguirre Pittman",
                    "gender": "male",
                    "company": "ENERFORCE",
                    "email": "aguirrepittman@enerforce.com",
                    "phone": "\\\\Q+1 (968) 414-2754\\\\E",
                    "address": "725 Stewart Street, Roosevelt, Federated States Of Micronesia, 7358",
                    "about": "Nostrud mollit magna non anim pariatur ipsum eiusmod. Quis adipisicing minim velit ullamco non eu do sit deserunt aliqua amet id. Do minim labore dolor aliquip. Minim elit do culpa ullamco tempor eu.\\r\\n",
                    "registered": "2016-10-24T01:36:32 -03:00",
                    "latitude": -85.212623,
                    "longitude": -70.08197,
                    "tags": [
                      "excepteur",
                      "sit",
                      "tempor",
                      "reprehenderit",
                      "labore",
                      "ad",
                      "quis"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Compton Page"
                      },
                      {
                        "id": 1,
                        "name": "Lauri Head"
                      },
                      {
                        "id": 2,
                        "name": "Josefina Valdez"
                      }
                    ],
                    "greeting": "Hello, Aguirre Pittman! You have 4 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b32b7550a455add657",
                    "index": 4,
                    "guid": "4d479205-50f7-4450-b8de-fca018ed9ed6",
                    "isActive": false,
                    "balance": "\\\\Q$1,237.74\\\\E",
                    "picture": "http://placehold.it/32x32",
                    "age": 29,
                    "eyeColor": "brown",
                    "name": "Linda Todd",
                    "gender": "female",
                    "company": "EARGO",
                    "email": "lindatodd@eargo.com",
                    "phone": "\\\\Q+1 (935) 458-2135\\\\E",
                    "address": "220 Wortman Avenue, Troy, Alaska, 4176",
                    "about": "Qui exercitation adipisicing qui officia velit non minim sint reprehenderit in cupidatat consequat. Tempor duis deserunt fugiat adipisicing nulla voluptate exercitation enim sit. Sint laborum nisi sunt eiusmod consectetur mollit.\\r\\n",
                    "registered": "2017-01-24T06:16:47 -02:00",
                    "latitude": 9.690775,
                    "longitude": -163.158716,
                    "tags": [
                      "est",
                      "mollit",
                      "sunt",
                      "officia",
                      "veniam",
                      "velit",
                      "elit"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Maritza Holder"
                      },
                      {
                        "id": 1,
                        "name": "Paulette Paul"
                      },
                      {
                        "id": 2,
                        "name": "Anastasia Rodriguez"
                      }
                    ],
                    "greeting": "Hello, Linda Todd! You have 4 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b3cf431f57da512bb3",
                    "index": 5,
                    "guid": "15d3f431-7ff5-4e0d-bc6a-f193f1d311d6",
                    "isActive": true,
                    "balance": "\\\\Q$1,807.59\\\\E",
                    "picture": "http://placehold.it/32x32",
                    "age": 34,
                    "eyeColor": "green",
                    "name": "Trudy Wooten",
                    "gender": "female",
                    "company": "BUZZOPIA",
                    "email": "trudywooten@buzzopia.com",
                    "phone": "\\\\Q+1 (874) 420-3915\\\\E",
                    "address": "414 Pershing Loop, Yonah, Marshall Islands, 3699",
                    "about": "Consectetur minim ea aliquip exercitation. Do pariatur irure sunt aliquip occaecat dolor mollit proident aliqua veniam. Ex mollit non do voluptate culpa mollit qui laborum esse aliqua enim.\\r\\n",
                    "registered": "2015-07-17T07:01:03 -03:00",
                    "latitude": 88.764821,
                    "longitude": 168.985813,
                    "tags": [
                      "exercitation",
                      "qui",
                      "anim",
                      "eu",
                      "aliquip",
                      "id",
                      "magna"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Thelma Obrien"
                      },
                      {
                        "id": 1,
                        "name": "Cabrera Campbell"
                      },
                      {
                        "id": 2,
                        "name": "Mcfarland Harrington"
                      }
                    ],
                    "greeting": "Hello, Trudy Wooten! You have 2 unread messages.",
                    "favoriteFruit": "apple"
                  }
                ]
                """;
        String actual = """
                [
                  {
                    "_id": "5b1fc6b3ffc466654b645469",
                    "index": 0,
                    "guid": "f0b70f36-549d-4660-ba96-3fb6d4be5517",
                    "isActive": true,
                    "balance": "$1,029.68",
                    "picture": "http://placehold.it/32x32",
                    "eyeColor": "blue",
                    "name": "Lori Stephenson",
                    "gender": "female",
                    "company": "ZILLACON",
                    "email": "loristephenson@zillacon.com",
                    "phone": "+1 (896) 542-2167",
                    "address": "830 Claver Place, Juarez, New Mexico, 8301",
                    "about": "Irure esse ad ullamco velit pariatur excepteur eiusmod ut commodo. In incididunt sunt sit et occaecat culpa aliqua sunt aute fugiat nostrud pariatur non dolor. Sint ad ullamco aliqua reprehenderit et irure laborum nostrud voluptate elit dolore in ad. Enim nulla in incididunt sunt consequat ullamco amet ipsum magna officia dolor.\\r\\n",
                    "registered": "2015-05-04T08:36:29 -03:00",
                    "latitude": -16.071608,
                    "longitude": -59.93564,
                    "age": 29,
                    "tags": [
                      "do",
                      "ut",
                      "pariatur",
                      "ut",
                      "aliquip",
                      "laborum",
                      "magna"
                    ],
                    "greeting": "Hello, Lori Stephenson! You have 9 unread messages.",
                    "friends": [
                      {
                        "id": 0,
                        "name": "Carroll Strickland"
                      },
                      {
                        "id": 1,
                        "name": "Esperanza Weiss"
                      },
                      {
                        "id": 2,
                        "name": "Kinney Hudson"
                      }
                    ],
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b346caf914332726b9",
                    "index": 1,
                    "guid": "d5656dec-6db7-401b-98eb-ea4fafc7cdca",
                    "isActive": false,
                    "balance": "$1,907.04",
                    "picture": "http://placehold.it/32x32",
                    "name": "Dorothy Pate",
                    "gender": "female",
                    "company": "PYRAMIA",
                    "email": "dorothypate@pyramia.com",
                    "phone": "+1 (872) 483-2665",
                    "address": "615 Meeker Avenue, Hiwasse, Missouri, 8757",
                    "about": "Officia est in Lorem aliqua ad velit. Voluptate velit pariatur ex eiusmod. Id nulla quis veniam qui consequat ad ea non esse nisi mollit labore ea laboris. Aliqua sit deserunt duis non mollit consequat proident veniam. Commodo cillum proident irure et deserunt veniam sunt consequat irure velit ea incididunt culpa labore. Excepteur exercitation eiusmod laboris minim Lorem.\\r\\n",
                    "registered": "2014-04-03T07:02:38 -03:00",
                    "latitude": 24.589299,
                    "longitude": 163.036427,
                    "age": 36,
                    "eyeColor": "brown",
                    "tags": [
                      "ut",
                      "sunt",
                      "tempor",
                      "commodo",
                      "nulla",
                      "cupidatat",
                      "veniam"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Louella Maxwell"
                      },
                      {
                        "id": 1,
                        "name": "Yang Holland"
                      },
                      {
                        "id": 2,
                        "name": "Bobbi Delgado"
                      }
                    ],
                    "greeting": "Hello, Dorothy Pate! You have 7 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b3a746b2ffaf7fc480",
                    "index": 2,
                    "guid": "a20dbd35-4210-47ad-b8eb-354af186851f",
                    "isActive": true,
                    "balance": "$2,733.81",
                    "picture": "http://placehold.it/32x32",
                    "age": 24,
                    "eyeColor": "brown",
                    "name": "Salazar Ryan",
                    "gender": "male",
                    "company": "ENOMEN",
                    "email": "salazarryan@enomen.com",
                    "phone": "+1 (833) 504-2359",
                    "address": "902 Paerdegat Avenue, Madaket, Northern Mariana Islands, 8664",
                    "about": "Nostrud est do id ut. Elit laboris consequat quis ea id Lorem consectetur sit. Sit aliquip incididunt ipsum nisi excepteur cupidatat aliquip. Enim aliquip culpa esse ad in qui quis enim dolor laborum sit. Incididunt pariatur cupidatat culpa anim sunt aute enim irure aliquip. Eu veniam irure enim magna. Amet dolore cupidatat enim in incididunt non amet irure magna.\\r\\n",
                    "registered": "2015-05-07T09:04:10 -03:00",
                    "latitude": -6.248778,
                    "longitude": 50.080523,
                    "tags": [
                      "labore",
                      "consequat",
                      "magna",
                      "officia",
                      "exercitation",
                      "exercitation",
                      "dolore"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Mercado Valencia"
                      },
                      {
                        "id": 1,
                        "name": "Jordan Finley"
                      },
                      {
                        "id": 2,
                        "name": "Malone Chapman"
                      }
                    ],
                    "greeting": "Hello, Salazar Ryan! You have 3 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b3b7ac57348b1664c7",
                    "index": 3,
                    "guid": "739ee20a-7892-4fb6-8c82-ec8f0ea60a21",
                    "isActive": true,
                    "balance": "$1,272.76",
                    "picture": "http://placehold.it/32x32",
                    "age": 27,
                    "eyeColor": "green",
                    "name": "Aguirre Pittman",
                    "gender": "male",
                    "company": "ENERFORCE",
                    "email": "aguirrepittman@enerforce.com",
                    "phone": "+1 (968) 414-2754",
                    "address": "725 Stewart Street, Roosevelt, Federated States Of Micronesia, 7358",
                    "about": "Nostrud mollit magna non anim pariatur ipsum eiusmod. Quis adipisicing minim velit ullamco non eu do sit deserunt aliqua amet id. Do minim labore dolor aliquip. Minim elit do culpa ullamco tempor eu.\\r\\n",
                    "registered": "2016-10-24T01:36:32 -03:00",
                    "latitude": -85.212623,
                    "longitude": -70.08197,
                    "tags": [
                      "excepteur",
                      "sit",
                      "tempor",
                      "reprehenderit",
                      "labore",
                      "ad",
                      "quis"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Compton Page"
                      },
                      {
                        "id": 1,
                        "name": "Lauri Head"
                      },
                      {
                        "id": 2,
                        "name": "Josefina Valdez"
                      }
                    ],
                    "greeting": "Hello, Aguirre Pittman! You have 4 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b32b7550a455add657",
                    "index": 4,
                    "guid": "4d479205-50f7-4450-b8de-fca018ed9ed6",
                    "isActive": false,
                    "balance": "$1,237.74",
                    "picture": "http://placehold.it/32x32",
                    "age": 29,
                    "eyeColor": "brown",
                    "name": "Linda Todd",
                    "gender": "female",
                    "company": "EARGO",
                    "email": "lindatodd@eargo.com",
                    "phone": "+1 (935) 458-2135",
                    "address": "220 Wortman Avenue, Troy, Alaska, 4176",
                    "about": "Qui exercitation adipisicing qui officia velit non minim sint reprehenderit in cupidatat consequat. Tempor duis deserunt fugiat adipisicing nulla voluptate exercitation enim sit. Sint laborum nisi sunt eiusmod consectetur mollit.\\r\\n",
                    "registered": "2017-01-24T06:16:47 -02:00",
                    "latitude": 9.690775,
                    "longitude": -163.158716,
                    "tags": [
                      "est",
                      "mollit",
                      "sunt",
                      "officia",
                      "veniam",
                      "velit",
                      "elit"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Maritza Holder"
                      },
                      {
                        "id": 1,
                        "name": "Paulette Paul"
                      },
                      {
                        "id": 2,
                        "name": "Anastasia Rodriguez"
                      }
                    ],
                    "greeting": "Hello, Linda Todd! You have 4 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b3cf431f57da512bb3",
                    "index": 5,
                    "guid": "15d3f431-7ff5-4e0d-bc6a-f193f1d311d6",
                    "isActive": true,
                    "balance": "$1,807.59",
                    "picture": "http://placehold.it/32x32",
                    "age": 34,
                    "eyeColor": "green",
                    "name": "Trudy Wooten",
                    "gender": "female",
                    "company": "BUZZOPIA",
                    "email": "trudywooten@buzzopia.com",
                    "phone": "+1 (874) 420-3915",
                    "address": "414 Pershing Loop, Yonah, Marshall Islands, 3699",
                    "about": "Consectetur minim ea aliquip exercitation. Do pariatur irure sunt aliquip occaecat dolor mollit proident aliqua veniam. Ex mollit non do voluptate culpa mollit qui laborum esse aliqua enim.\\r\\n",
                    "registered": "2015-07-17T07:01:03 -03:00",
                    "latitude": 88.764821,
                    "longitude": 168.985813,
                    "tags": [
                      "exercitation",
                      "qui",
                      "anim",
                      "eu",
                      "aliquip",
                      "id",
                      "magna"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Thelma Obrien"
                      },
                      {
                        "id": 1,
                        "name": "Cabrera Campbell"
                      },
                      {
                        "id": 2,
                        "name": "Mcfarland Harrington"
                      }
                    ],
                    "greeting": "Hello, Trudy Wooten! You have 2 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b3f8332f14e4220e7b",
                    "index": 6,
                    "guid": "6c5c4b4e-9796-406c-9ac5-a1961a189210",
                    "isActive": false,
                    "balance": "$1,576.99",
                    "picture": "http://placehold.it/32x32",
                    "age": 30,
                    "eyeColor": "blue",
                    "name": "Boyd Nixon",
                    "gender": "male",
                    "company": "UNQ",
                    "email": "boydnixon@unq.com",
                    "phone": "+1 (861) 544-3022",
                    "address": "513 Monroe Place, Detroit, Louisiana, 6877",
                    "about": "Eu duis ut velit et enim labore excepteur nisi sit id adipisicing incididunt excepteur. Ipsum culpa ut aliquip voluptate enim commodo irure excepteur culpa. Magna ut nulla culpa esse tempor amet nisi Lorem consequat adipisicing.\\r\\n",
                    "registered": "2016-01-29T03:01:18 -02:00",
                    "latitude": 23.463126,
                    "longitude": -152.959073,
                    "tags": [
                      "fugiat",
                      "aliqua",
                      "incididunt",
                      "sint",
                      "adipisicing",
                      "labore",
                      "aliquip"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Patrica Summers"
                      },
                      {
                        "id": 1,
                        "name": "Mandy Alvarez"
                      },
                      {
                        "id": 2,
                        "name": "Stone Owens"
                      }
                    ],
                    "greeting": "Hello, Boyd Nixon! You have 5 unread messages.",
                    "favoriteFruit": "strawberry"
                  },
                  {
                    "_id": "5b1fc6b331c55ff5d56d2c57",
                    "index": 7,
                    "guid": "2c94eaa5-e1e3-470c-88c7-8c2683240377",
                    "isActive": true,
                    "balance": "$2,555.93",
                    "picture": "http://placehold.it/32x32",
                    "age": 38,
                    "eyeColor": "blue",
                    "name": "Gallagher Calderon",
                    "gender": "male",
                    "company": "NIMON",
                    "email": "gallaghercalderon@nimon.com",
                    "phone": "+1 (948) 532-2857",
                    "address": "439 Stryker Court, Nanafalia, Arkansas, 1228",
                    "about": "Officia eu do exercitation officia ex Lorem minim ipsum duis. Eu enim sint laboris nulla irure consectetur fugiat occaecat incididunt cupidatat. Nisi Lorem occaecat tempor ipsum sint cupidatat id anim ea non occaecat excepteur elit cillum.\\r\\n",
                    "registered": "2016-01-07T06:49:14 -02:00",
                    "latitude": 26.159356,
                    "longitude": -45.713632,
                    "tags": [
                      "anim",
                      "officia",
                      "esse",
                      "cillum",
                      "cupidatat",
                      "est",
                      "incididunt"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "West Jimenez"
                      },
                      {
                        "id": 1,
                        "name": "Madden Hahn"
                      },
                      {
                        "id": 2,
                        "name": "Hicks Burnett"
                      }
                    ],
                    "greeting": "Hello, Gallagher Calderon! You have 7 unread messages.",
                    "favoriteFruit": "strawberry"
                  },
                  {
                    "_id": "5b1fc6b3d0459a2558d714b5",
                    "index": 8,
                    "guid": "78e02ac7-fc77-4528-8e9d-f48811c06c7e",
                    "isActive": false,
                    "balance": "$1,557.59",
                    "picture": "http://placehold.it/32x32",
                    "age": 36,
                    "eyeColor": "brown",
                    "name": "Sharon Johns",
                    "about": "Fugiat consectetur in magna id occaecat sit aliquip ea ea eiusmod nisi dolor veniam consectetur. Exercitation eiusmod ut ad in culpa ut reprehenderit aliqua Lorem magna enim adipisicing veniam aute. Eu quis pariatur irure nisi deserunt. Labore velit deserunt excepteur deserunt excepteur adipisicing sint ea voluptate dolor reprehenderit eiusmod Lorem. Exercitation est proident eiusmod consectetur dolor pariatur Lorem veniam do incididunt. Aliquip non pariatur mollit ut consectetur minim aliqua. Excepteur sunt cillum proident aliquip eiusmod amet ipsum Lorem esse ad.\\r\\n",
                    "gender": "female",
                    "company": "ACUMENTOR",
                    "email": "sharonjohns@acumentor.com",
                    "phone": "+1 (973) 409-3797",
                    "address": "695 Thames Street, Hinsdale, Connecticut, 479",
                    "registered": "2016-07-01T03:28:01 -03:00",
                    "latitude": 84.257902,
                    "longitude": 144.671579,
                    "tags": [
                      "do",
                      "do",
                      "deserunt",
                      "aute",
                      "do",
                      "ut",
                      "velit"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Lenora Cochran"
                      },
                      {
                        "id": 1,
                        "name": "Bolton Mathews"
                      },
                      {
                        "id": 2,
                        "name": "Walls Bridges"
                      }
                    ],
                    "greeting": "Hello, Sharon Johns! You have 3 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b3a94c8578e056b2c4",
                    "index": 9,
                    "guid": "f7b153b1-d7d2-480a-be75-b855a06bd231",
                    "isActive": true,
                    "balance": "$1,005.55",
                    "picture": "http://placehold.it/32x32",
                    "age": 25,
                    "eyeColor": "green",
                    "name": "Pate Yates",
                    "gender": "male",
                    "company": "GENMOM",
                    "email": "pateyates@genmom.com",
                    "phone": "+1 (944) 571-2086",
                    "address": "846 Hampton Place, Sultana, Rhode Island, 2728",
                    "about": "Ex magna veniam dolore cillum amet Lorem excepteur mollit excepteur ut nisi ex Lorem non. Labore culpa sint nostrud voluptate cupidatat aute nisi reprehenderit minim et culpa pariatur id nisi. Qui irure do cupidatat et sit culpa amet ullamco fugiat officia magna enim.\\r\\n",
                    "registered": "2017-01-27T10:01:13 -02:00",
                    "latitude": -88.097111,
                    "longitude": -10.476844,
                    "tags": [
                      "elit",
                      "do",
                      "sit",
                      "eiusmod",
                      "occaecat",
                      "veniam",
                      "nisi"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Robles Santos"
                      },
                      {
                        "id": 1,
                        "name": "Ortega Whitley"
                      },
                      {
                        "id": 2,
                        "name": "Eunice Osborne"
                      }
                    ],
                    "greeting": "Hello, Pate Yates! You have 7 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b350d78bedaf096052",
                    "index": 10,
                    "guid": "bf079cfe-3753-4641-a8b1-488227ffe157",
                    "isActive": false,
                    "balance": "$3,389.64",
                    "picture": "http://placehold.it/32x32",
                    "age": 21,
                    "eyeColor": "brown",
                    "name": "Shannon Whitney",
                    "gender": "male",
                    "company": "WEBIOTIC",
                    "email": "shannonwhitney@webiotic.com",
                    "phone": "+1 (893) 565-3758",
                    "address": "892 Herbert Street, Irwin, Mississippi, 3383",
                    "about": "Ipsum cupidatat veniam ipsum voluptate dolore. Eu et aute ipsum velit esse aliquip id anim veniam enim ullamco. Laboris sunt tempor laboris irure esse nulla eiusmod qui excepteur tempor do ullamco culpa labore. Incididunt non in nostrud consectetur mollit consequat irure.\\r\\n",
                    "registered": "2018-05-11T02:42:17 -03:00",
                    "latitude": -36.559857,
                    "longitude": -96.966372,
                    "tags": [
                      "eiusmod",
                      "est",
                      "aliqua",
                      "aliquip",
                      "culpa",
                      "consectetur",
                      "occaecat"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Kristi Fields"
                      },
                      {
                        "id": 1,
                        "name": "Avila Conner"
                      },
                      {
                        "id": 2,
                        "name": "Cleo Hurst"
                      }
                    ],
                    "greeting": "Hello, Shannon Whitney! You have 9 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b35c9b382e79bf04fe",
                    "index": 11,
                    "guid": "5cfe75d1-da1d-471c-a282-0ceaa5dbf0df",
                    "isActive": true,
                    "balance": "$1,648.26",
                    "picture": "http://placehold.it/32x32",
                    "age": 28,
                    "eyeColor": "green",
                    "name": "Genevieve Mullen",
                    "gender": "female",
                    "company": "COMVEYOR",
                    "email": "genevievemullen@comveyor.com",
                    "phone": "+1 (983) 439-3024",
                    "address": "575 Christopher Avenue, Wells, Colorado, 3721",
                    "about": "Eiusmod cillum irure sint cupidatat do culpa do in pariatur anim magna. Lorem duis amet non aliqua ut magna excepteur ullamco id culpa velit incididunt qui dolor. Duis est qui esse velit velit laborum cupidatat duis sunt qui est pariatur irure. Qui qui magna id laborum. Aute id exercitation reprehenderit sint id esse est sunt aliquip magna consequat.\\r\\n",
                    "registered": "2017-06-27T11:27:36 -03:00",
                    "latitude": -20.462091,
                    "longitude": 53.85973,
                    "tags": [
                      "nostrud",
                      "reprehenderit",
                      "consectetur",
                      "sint",
                      "voluptate",
                      "duis",
                      "est"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Marisol Webster"
                      },
                      {
                        "id": 1,
                        "name": "White Hicks"
                      },
                      {
                        "id": 2,
                        "name": "Mack Gibbs"
                      }
                    ],
                    "greeting": "Hello, Genevieve Mullen! You have 2 unread messages.",
                    "favoriteFruit": "strawberry"
                  },
                  {
                    "_id": "5b1fc6b3e175179c9cef38c1",
                    "index": 12,
                    "guid": "0d9e438b-9dbc-42ca-bdf2-c293728e8adc",
                    "isActive": true,
                    "balance": "$3,966.86",
                    "picture": "http://placehold.it/32x32",
                    "age": 35,
                    "eyeColor": "blue",
                    "name": "Benton Klein",
                    "gender": "male",
                    "company": "NIXELT",
                    "email": "bentonklein@nixelt.com",
                    "phone": "+1 (883) 584-2089",
                    "address": "724 Beekman Place, Stouchsburg, West Virginia, 5184",
                    "about": "Proident nisi elit consectetur consequat pariatur duis ullamco est esse sint sunt sint nostrud. Non non quis duis ea culpa Lorem ex minim. Nulla ad duis qui id commodo. Dolore sint anim laborum velit cillum exercitation culpa dolor in exercitation cupidatat est elit. Elit ipsum adipisicing reprehenderit dolor nisi duis ex duis magna incididunt incididunt mollit. Adipisicing veniam sunt culpa eiusmod tempor Lorem dolore ut adipisicing occaecat do sint proident tempor.\\r\\n",
                    "registered": "2016-01-07T05:33:42 -02:00",
                    "latitude": 53.145629,
                    "longitude": -54.119043,
                    "tags": [
                      "est",
                      "sit",
                      "amet",
                      "ea",
                      "consectetur",
                      "elit",
                      "aliqua"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Joyner Rosario"
                      },
                      {
                        "id": 1,
                        "name": "Marva Sweeney"
                      },
                      {
                        "id": 2,
                        "name": "Mona Boone"
                      }
                    ],
                    "greeting": "Hello, Benton Klein! You have 3 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b314366485d39e85ed",
                    "index": 13,
                    "guid": "5a977e03-6c60-427d-804d-14d3df422219",
                    "isActive": false,
                    "balance": "$2,586.02",
                    "picture": "http://placehold.it/32x32",
                    "age": 26,
                    "eyeColor": "brown",
                    "name": "Dollie Mueller",
                    "gender": "female",
                    "company": "QUILTIGEN",
                    "email": "dolliemueller@quiltigen.com",
                    "phone": "+1 (947) 543-3017",
                    "address": "626 Amboy Street, Convent, Indiana, 3304",
                    "about": "Cillum laboris aliqua deserunt ea dolore ullamco in esse ipsum ullamco aliqua. Exercitation tempor proident reprehenderit ipsum duis aute. Ut dolor excepteur deserunt nisi. Veniam amet ut aute anim ut aliqua excepteur voluptate deserunt velit culpa labore veniam nulla. Lorem consequat officia proident quis adipisicing culpa non duis ullamco pariatur qui dolore ex.\\r\\n",
                    "registered": "2017-03-14T10:17:50 -02:00",
                    "latitude": -51.779456,
                    "longitude": 63.511476,
                    "tags": [
                      "velit",
                      "excepteur",
                      "laborum",
                      "excepteur",
                      "ut",
                      "ex",
                      "aliqua"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Bender Kelly"
                      },
                      {
                        "id": 1,
                        "name": "Sanchez Madden"
                      },
                      {
                        "id": 2,
                        "name": "Clay Hoffman"
                      }
                    ],
                    "greeting": "Hello, Dollie Mueller! You have 6 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b31f9ce5cd76cc9e14",
                    "index": 14,
                    "guid": "1d4607c6-a7d3-4693-a4a6-b969e35e5651",
                    "isActive": false,
                    "balance": "$1,440.33",
                    "picture": "http://placehold.it/32x32",
                    "age": 27,
                    "eyeColor": "brown",
                    "name": "Baird Berry",
                    "gender": "male",
                    "company": "ACCIDENCY",
                    "email": "bairdberry@accidency.com",
                    "phone": "+1 (849) 451-2702",
                    "address": "464 Jodie Court, Hardyville, Florida, 3083",
                    "about": "Culpa nostrud exercitation nisi excepteur exercitation consequat quis elit ad ullamco. Ut laborum eu cillum officia id excepteur ullamco ex cupidatat sit eu elit amet. Pariatur dolore ipsum adipisicing aliqua elit Lorem aliqua ea. Incididunt aute in fugiat deserunt commodo sit anim. Nostrud dolore eiusmod nulla incididunt aliqua.\\r\\n",
                    "registered": "2017-03-24T01:31:56 -02:00",
                    "latitude": -10.966882,
                    "longitude": 43.470642,
                    "tags": [
                      "est",
                      "aute",
                      "deserunt",
                      "do",
                      "occaecat",
                      "magna",
                      "amet"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Valarie Avery"
                      },
                      {
                        "id": 1,
                        "name": "Tanner Matthews"
                      },
                      {
                        "id": 2,
                        "name": "Etta Irwin"
                      }
                    ],
                    "greeting": "Hello, Baird Berry! You have 7 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b375565a13ea216365",
                    "index": 15,
                    "guid": "fa0b45a1-34ef-4ec5-8f2a-3c4195215e0b",
                    "isActive": false,
                    "balance": "$2,098.35",
                    "picture": "http://placehold.it/32x32",
                    "age": 21,
                    "eyeColor": "brown",
                    "name": "Courtney Cross",
                    "gender": "female",
                    "company": "FIBEROX",
                    "email": "courtneycross@fiberox.com",
                    "phone": "+1 (950) 433-3147",
                    "address": "311 Fayette Street, Sanborn, New Jersey, 4875",
                    "about": "Voluptate labore nulla et ea elit. Aliquip labore cillum aliqua dolor ad ad sunt nisi. Sunt consectetur ad pariatur occaecat qui pariatur veniam. Laboris id quis laboris aliqua id velit officia consequat.\\r\\n",
                    "registered": "2015-07-10T12:25:58 -03:00",
                    "latitude": -46.388225,
                    "longitude": -153.147474,
                    "tags": [
                      "sunt",
                      "laboris",
                      "minim",
                      "nulla",
                      "veniam",
                      "sint",
                      "non"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Austin Spears"
                      },
                      {
                        "id": 1,
                        "name": "Rae Camacho"
                      },
                      {
                        "id": 2,
                        "name": "Harmon Livingston"
                      }
                    ],
                    "greeting": "Hello, Courtney Cross! You have 10 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b397fe1766769eb465",
                    "index": 16,
                    "guid": "c5f496fb-1e95-4531-9ae2-66c627ec3d3d",
                    "isActive": true,
                    "balance": "$1,347.10",
                    "picture": "http://placehold.it/32x32",
                    "age": 36,
                    "eyeColor": "brown",
                    "name": "Ayers Kerr",
                    "gender": "male",
                    "company": "CORPORANA",
                    "email": "ayerskerr@corporana.com",
                    "phone": "+1 (991) 489-2361",
                    "address": "596 Graham Avenue, Keyport, Delaware, 9139",
                    "about": "Culpa ad pariatur laborum cupidatat adipisicing occaecat consequat elit enim reprehenderit anim est reprehenderit. Consequat in elit proident mollit nulla. Ex enim excepteur ex labore pariatur velit. Laboris incididunt fugiat eu laboris cupidatat esse velit nulla pariatur ad pariatur esse reprehenderit.\\r\\n",
                    "registered": "2016-05-17T03:11:15 -03:00",
                    "latitude": 67.953667,
                    "longitude": 162.003308,
                    "tags": [
                      "non",
                      "irure",
                      "labore",
                      "minim",
                      "nostrud",
                      "ad",
                      "mollit"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Janet Hampton"
                      },
                      {
                        "id": 1,
                        "name": "Sonia Allen"
                      },
                      {
                        "id": 2,
                        "name": "Amie Roth"
                      }
                    ],
                    "greeting": "Hello, Ayers Kerr! You have 2 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b3766fe246b3cae522",
                    "index": 17,
                    "guid": "753a3eb0-6bb8-4bbe-95cb-44d0715f6343",
                    "isActive": true,
                    "balance": "$3,816.99",
                    "picture": "http://placehold.it/32x32",
                    "age": 23,
                    "eyeColor": "green",
                    "name": "Hunter Vega",
                    "gender": "male",
                    "company": "NETERIA",
                    "email": "huntervega@neteria.com",
                    "phone": "+1 (880) 411-3805",
                    "address": "819 Ford Street, Cassel, Pennsylvania, 961",
                    "about": "Excepteur sit laboris magna cupidatat laborum amet aliqua aute. Mollit magna mollit quis exercitation sit. Enim exercitation id in ad elit cupidatat ullamco quis in in velit nisi proident elit. Aliquip consequat aliquip sint dolore consectetur cillum ad et incididunt occaecat laborum irure adipisicing. Consectetur ex sint culpa et laboris exercitation aliqua irure qui adipisicing et deserunt esse. Non duis pariatur sit voluptate ullamco. Elit eiusmod nisi magna cillum enim dolor magna deserunt sit dolore aliquip occaecat.\\r\\n",
                    "registered": "2016-10-22T03:09:17 -03:00",
                    "latitude": -41.888408,
                    "longitude": 111.835503,
                    "tags": [
                      "ea",
                      "et",
                      "duis",
                      "nisi",
                      "est",
                      "nostrud",
                      "nisi"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Rosetta Tyler"
                      },
                      {
                        "id": 1,
                        "name": "Parrish Swanson"
                      },
                      {
                        "id": 2,
                        "name": "Noreen Frazier"
                      }
                    ],
                    "greeting": "Hello, Hunter Vega! You have 8 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b3fd585229aa056326",
                    "index": 18,
                    "guid": "25ece042-e6f7-433e-aafb-4e3f3fb79aaa",
                    "isActive": true,
                    "balance": "$3,418.60",
                    "picture": "http://placehold.it/32x32",
                    "age": 35,
                    "eyeColor": "green",
                    "name": "Sheri Payne",
                    "gender": "female",
                    "company": "TETAK",
                    "email": "sheripayne@tetak.com",
                    "phone": "+1 (824) 530-2349",
                    "address": "278 Macon Street, Brutus, Virginia, 892",
                    "about": "Consequat laboris ipsum incididunt proident nostrud reprehenderit fugiat velit commodo eiusmod enim in cupidatat ullamco. Non ex duis est mollit dolor officia magna excepteur aliquip in eiusmod Lorem voluptate. Nisi duis in et est qui ut et. Adipisicing quis velit qui officia duis id. Voluptate veniam irure qui ad exercitation esse sunt. Aute nulla ipsum aliquip quis sit non deserunt amet aliqua incididunt adipisicing eiusmod.\\r\\n",
                    "registered": "2016-06-14T05:30:16 -03:00",
                    "latitude": -14.633416,
                    "longitude": 48.022597,
                    "tags": [
                      "officia",
                      "in",
                      "eiusmod",
                      "dolore",
                      "sunt",
                      "proident",
                      "irure"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Beulah Dyer"
                      },
                      {
                        "id": 1,
                        "name": "Moon Davis"
                      },
                      {
                        "id": 2,
                        "name": "Fox James"
                      }
                    ],
                    "greeting": "Hello, Sheri Payne! You have 8 unread messages.",
                    "favoriteFruit": "apple"
                  }
                ]
                """;
        JSONCompare.compare(expected, actual).assertMatches();
    }

    @Test
    public void compareBigJsons_negative() {
        String expected = """
                [
                  {
                    "_id": "5b1fc6b3ffc466654b645469",
                    "index": 0,
                    "guid": "f0b70f36-549d-4660-ba96-3fb6d4be5517",
                    "isActive": true,
                    "balance": "\\\\Q$1,029.68\\\\E",
                    "picture": "http://placehold.it/32x32",
                    "age": 29,
                    "eyeColor": "blue",
                    "name": "Lori Stephenson",
                    "gender": "female",
                    "company": "ZILLACON",
                    "email": "loristephenson@zillacon.com",
                    "phone": "\\\\Q+1 (896) 542-2167\\\\E",
                    "address": "830 Claver Place, Juarez, New Mexico, 8301",
                    "about": "Irure esse ad ullamco velit pariatur excepteur eiusmod ut commodo. In incididunt sunt sit et occaecat culpa aliqua sunt aute fugiat nostrud pariatur non dolor. Sint ad ullamco aliqua reprehenderit et irure laborum nostrud voluptate elit dolore in ad. Enim nulla in incididunt sunt consequat ullamco amet ipsum magna officia dolor.\\r\\n",
                    "registered": "2015-05-04T08:36:29 -03:00",
                    "latitude": -16.071608,
                    "longitude": -59.93564,
                    "tags": [
                      "do",
                      "aliquip",
                      "laborum",
                      "magna",
                      "ut",
                      "pariatur",
                      "ut"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Carroll Strickland"
                      },
                      {
                        "id": 1,
                        "name": "Esperanza Weiss"
                      },
                      {
                        "id": 2,
                        "name": "Kinney Hudson"
                      }
                    ],
                    "greeting": "Hello, Lori Stephenson! You have 9 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b346caf914332726b9",
                    "index": 1,
                    "guid": "d5656dec-6db7-401b-98eb-ea4fafc7cdca",
                    "isActive": false,
                    "balance": "\\\\Q$1,907.04\\\\E",
                    "picture": "http://placehold.it/32x32",
                    "age": 36,
                    "eyeColor": "brown",
                    "name": "Dorothy Pate",
                    "gender": "female",
                    "company": "PYRAMIA",
                    "email": "dorothypate@pyramia.com",
                    "phone": "\\\\Q+1 (872) 483-2665\\\\E",
                    "address": "615 Meeker Avenue, Hiwasse, Missouri, 8757",
                    "about": "Officia est in Lorem aliqua ad velit. Voluptate velit pariatur ex eiusmod. Id nulla quis veniam qui consequat ad ea non esse nisi mollit labore ea laboris. Aliqua sit deserunt duis non mollit consequat proident veniam. Commodo cillum proident irure et deserunt veniam sunt consequat irure velit ea incididunt culpa labore. Excepteur exercitation eiusmod laboris minim Lorem.\\r\\n",
                    "registered": "2014-04-03T07:02:38 -03:00",
                    "latitude": 24.589299,
                    "longitude": 163.036427,
                    "tags": [
                      "ut",
                      "sunt",
                      "tempor",
                      "commodo",
                      "nulla",
                      "cupidatat",
                      "veniam"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Louella Maxwell"
                      },
                      {
                        "id": 1,
                        "name": "Yang Holland"
                      },
                      {
                        "id": 2,
                        "name": "Bobbi Delgado"
                      }
                    ],
                    "greeting": "Hello, Dorothy Pate! You have 7 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b3a746b2ffaf7fc480",
                    "index": 2,
                    "guid": "a20dbd35-4210-47ad-b8eb-354af186851f",
                    "isActive": true,
                    "balance": "\\\\Q$2,733.81\\\\E",
                    "picture": "http://placehold.it/32x32",
                    "age": 24,
                    "eyeColor": "brown",
                    "name": "Salazar Ryan",
                    "gender": "male",
                    "company": "ENOMEN",
                    "email": "salazarryan@enomen.com",
                    "phone": "\\\\Q+1 (833) 504-2359\\\\E",
                    "address": "902 Paerdegat Avenue, Madaket, Northern Mariana Islands, 8664",
                    "about": "Nostrud est do id ut. Elit laboris consequat quis ea id Lorem consectetur sit. Sit aliquip incididunt ipsum nisi excepteur cupidatat aliquip. Enim aliquip culpa esse ad in qui quis enim dolor laborum sit. Incididunt pariatur cupidatat culpa anim sunt aute enim irure aliquip. Eu veniam irure enim magna. Amet dolore cupidatat enim in incididunt non amet irure magna.\\r\\n",
                    "registered": "2015-05-07T09:04:10 -03:00",
                    "latitude": -6.248778,
                    "longitude": 50.080523,
                    "tags": [
                      "labore",
                      "consequat",
                      "magna",
                      "officia",
                      "exercitation",
                      "exercitation",
                      "dolore"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Mercado Valencia"
                      },
                      {
                        "id": 1,
                        "name": "Jordan Finley"
                      },
                      {
                        "id": 2,
                        "name": "Malone Chapman"
                      }
                    ],
                    "greeting": "Hello, Salazar Ryan! You have 3 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b3b7ac57348b1664c7",
                    "index": 3,
                    "guid": "739ee20a-7892-4fb6-8c82-ec8f0ea60a21",
                    "isActive": true,
                    "balance": "\\\\Q$1,272.76\\\\E",
                    "picture": "http://placehold.it/32x32",
                    "age": 27,
                    "eyeColor": "green",
                    "name": "Aguirre Pittman",
                    "gender": "male",
                    "company": "ENERFORCE",
                    "email": "aguirrepittman@enerforce.com",
                    "phone": "\\\\Q+1 (968) 414-2754\\\\E",
                    "address": "725 Stewart Street, Roosevelt, Federated States Of Micronesia, 7358",
                    "about": "Nostrud mollit magna non anim pariatur ipsum eiusmod. Quis adipisicing minim velit ullamco non eu do sit deserunt aliqua amet id. Do minim labore dolor aliquip. Minim elit do culpa ullamco tempor eu.\\r\\n",
                    "registered": "2016-10-24T01:36:32 -03:00",
                    "latitude": -85.212623,
                    "longitude": -70.08197,
                    "tags": [
                      "excepteur",
                      "sit",
                      "tempor",
                      "reprehenderit",
                      "labore",
                      "ad",
                      "quis"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Compton Page"
                      },
                      {
                        "id": 1,
                        "name": "Lauri Head"
                      },
                      {
                        "id": 2,
                        "name": "Josefina Valdez"
                      }
                    ],
                    "greeting": "Hello, Aguirre Pittman! You have 4 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b32b7550a455add657",
                    "index": 4,
                    "guid": "4d479205-50f7-4450-b8de-fca018ed9ed6",
                    "isActive": false,
                    "balance": "\\\\Q$1,237.74\\\\E",
                    "picture": "http://placehold.it/32x32",
                    "age": 29,
                    "eyeColor": "brown",
                    "name": "Linda Todd",
                    "gender": "female",
                    "company": "EARGO",
                    "email": "lindatodd@eargo.com",
                    "phone": "\\\\Q+1 (935) 458-2135\\\\E",
                    "address": "220 Wortman Avenue, Troy, Alaska, 4176",
                    "about": "Qui exercitation adipisicing qui officia velit non minim sint reprehenderit in cupidatat consequat. Tempor duis deserunt fugiat adipisicing nulla voluptate exercitation enim sit. Sint laborum nisi sunt eiusmod consectetur mollit.\\r\\n",
                    "registered": "2017-01-24T06:16:47 -02:00",
                    "latitude": 9.690775,
                    "longitude": -163.158716,
                    "tags": [
                      "est",
                      "mollit",
                      "sunt",
                      "officia",
                      "veniam",
                      "velit",
                      "elit"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Maritza Holder"
                      },
                      {
                        "id": 1,
                        "name": "Paulette Paul"
                      },
                      {
                        "id": 2,
                        "name": "Anastasia Rodriguez"
                      }
                    ],
                    "greeting": "Hello, Linda Todd! You have 4 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b3cf431f57da512bb3",
                    "index": 5,
                    "guid": "15d3f431-7ff5-4e0d-bc6a-f193f1d311d6",
                    "isActive": true,
                    "balance": "\\\\Q$1,807.59\\\\E",
                    "picture": "http://placehold.it/32x32",
                    "age": 34,
                    "eyeColor": "green",
                    "name": "Trudy Wooten",
                    "gender": "female",
                    "company": "BUZZOPIA",
                    "email": "trudywooten@buzzopia.com",
                    "phone": "\\\\Q+1 (874) 420-3915\\\\E",
                    "address": "414 Pershing Loop, Yonah, Marshall Islands, 3699",
                    "about": "Consectetur minim ea aliquip exercitation. Do pariatur irure sunt aliquip occaecat dolor mollit proident aliqua veniam. Ex mollit non do voluptate culpa mollit qui laborum esse aliqua enim.\\r\\n",
                    "registered": "2015-07-17T07:01:03 -03:00",
                    "latitude": 88.764821,
                    "longitude": 168.985813,
                    "tags": [
                      "exercitation",
                      "qui",
                      "anim",
                      "eu",
                      "aliquip",
                      "id",
                      "magna"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Thelma Obrien"
                      },
                      {
                        "id": 1,
                        "name": "Cabrera Campbell"
                      },
                      {
                        "id": 2,
                        "name": "Mcfarland Harrington"
                      }
                    ],
                    "greeting": "Hello, Trudy Wooten! You have 2 unread messages.",
                    "favoriteFruit": "apple"
                  }
                ]
                """;
        String actual = """
                [
                  {
                    "_id": "5b1fc6b3ffc466654b645469",
                    "index": 0,
                    "guid": "f0b70f36-549d-4660-ba96-3fb6d4be5517",
                    "isActive": true,
                    "balance": "$1,029.68",
                    "picture": "http://placehold.it/32x32",
                    "eyeColor": "blue",
                    "name": "Lori Stephenson",
                    "gender": "female",
                    "company": "ZILLACON",
                    "email": "loristephenson@zillacon.com",
                    "phone": "+1 (896) 542-2167",
                    "address": "830 Claver Place, Juarez, New Mexico, 8301",
                    "about": "Irure esse ad ullamco velit pariatur excepteur eiusmod ut commodo. In incididunt sunt sit et occaecat culpa aliqua sunt aute fugiat nostrud pariatur non dolor. Sint ad ullamco aliqua reprehenderit et irure laborum nostrud voluptate elit dolore in ad. Enim nulla in incididunt sunt consequat ullamco amet ipsum magna officia dolor.\\r\\n",
                    "registered": "2015-05-04T08:36:29 -03:00",
                    "latitude": -16.071608,
                    "longitude": -59.93564,
                    "age": 29,
                    "tags": [
                      "do",
                      "ut",
                      "pariatur",
                      "ut",
                      "aliquip",
                      "laborum",
                      "magna"
                    ],
                    "greeting": "Hello, Lori Stephenson! You have 9 unread messages.",
                    "friends": [
                      {
                        "id": 0,
                        "name": "Carroll Strickland"
                      },
                      {
                        "id": 1,
                        "name": "Esperanza Weiss"
                      },
                      {
                        "id": 2,
                        "name": "Kinney Hudson"
                      }
                    ],
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b346caf914332726b9",
                    "index": 1,
                    "guid": "d5656dec-6db7-401b-98eb-ea4fafc7cdca",
                    "isActive": false,
                    "balance": "$1,907.04",
                    "picture": "http://placehold.it/32x32",
                    "name": "Dorothy Pate",
                    "gender": "female",
                    "company": "PYRAMIA",
                    "email": "dorothypate@pyramia.com",
                    "phone": "+1 (872) 483-2665",
                    "address": "615 Meeker Avenue, Hiwasse, Missouri, 8757",
                    "about": "Officia est in Lorem aliqua ad velit. Voluptate velit pariatur ex eiusmod. Id nulla quis veniam qui consequat ad ea non esse nisi mollit labore ea laboris. Aliqua sit deserunt duis non mollit consequat proident veniam. Commodo cillum proident irure et deserunt veniam sunt consequat irure velit ea incididunt culpa labore. Excepteur exercitation eiusmod laboris minim Lorem.\\r\\n",
                    "registered": "2014-04-03T07:02:38 -03:00",
                    "latitude": 24.589299,
                    "longitude": 163.036427,
                    "age": 36,
                    "eyeColor": "brown",
                    "tags": [
                      "ut",
                      "sunt",
                      "tempor",
                      "commodo",
                      "nulla",
                      "cupidatat",
                      "veniam"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Louella Maxwell"
                      },
                      {
                        "id": 1,
                        "name": "Yang Holland"
                      },
                      {
                        "id": 2,
                        "name": "Bobbi Delgado"
                      }
                    ],
                    "greeting": "Hello, Dorothy Pate! You have 7 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b3a746b2ffaf7fc480",
                    "index": 2,
                    "guid": "a20dbd35-4210-47ad-b8eb-354af186851f",
                    "isActive": true,
                    "balance": "$2,733.81",
                    "picture": "http://placehold.it/32x32",
                    "age": 24,
                    "eyeColor": "brown",
                    "name": "Salazar Ryan",
                    "gender": "male",
                    "company": "ENOMEN",
                    "email": "salazarryan@enomen.com",
                    "phone": "+1 (833) 504-2359",
                    "address": "902 Paerdegat Avenue, Madaket, Northern Mariana Islands, 8664",
                    "about": "Nostrud est do id ut. Elit laboris consequat quis ea id Lorem consectetur sit. Sit aliquip incididunt ipsum nisi excepteur cupidatat aliquip. Enim aliquip culpa esse ad in qui quis enim dolor laborum sit. Incididunt pariatur cupidatat culpa anim sunt aute enim irure aliquip. Eu veniam irure enim magna. Amet dolore cupidatat enim in incididunt non amet irure magna.\\r\\n",
                    "registered": "2015-05-07T09:04:10 -03:00",
                    "latitude": -6.248778,
                    "longitude": 50.080523,
                    "tags": [
                      "labore",
                      "consequat",
                      "magna",
                      "officia",
                      "exercitation",
                      "exercitation",
                      "dolore"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Mercado Valencia"
                      },
                      {
                        "id": 1,
                        "name": "Jordan Finley"
                      },
                      {
                        "id": 2,
                        "name": "Malone Chapman"
                      }
                    ],
                    "greeting": "Hello, Salazar Ryan! You have 3 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b3b7ac57348b1664c7",
                    "index": 3,
                    "guid": "739ee20a-7892-4fb6-8c82-ec8f0ea60a21",
                    "isActive": true,
                    "balance": "$1,272.76",
                    "picture": "http://placehold.it/32x32",
                    "age": 27,
                    "eyeColor": "green",
                    "name": "Aguirre Pittman",
                    "gender": "male",
                    "company": "ENERFORCE",
                    "email": "aguirrepittman@enerforce.com",
                    "phone": "+1 (968) 414-2754",
                    "address": "725 Stewart Street, Roosevelt, Federated States Of Micronesia, 7358",
                    "about": "Nostrud mollit magna non anim pariatur ipsum eiusmod. Quis adipisicing minim velit ullamco non eu do sit deserunt aliqua amet id. Do minim labore dolor aliquip. Minim elit do culpa ullamco tempor eu.\\r\\n",
                    "registered": "2016-10-24T01:36:32 -03:00",
                    "latitude": -85.212623,
                    "longitude": -70.08197,
                    "tags": [
                      "excepteur",
                      "sit",
                      "tempor",
                      "reprehenderit",
                      "labore",
                      "ad",
                      "quis"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Compton Page"
                      },
                      {
                        "id": 10,
                        "name": "Lauri Head"
                      },
                      {
                        "id": 2,
                        "name": "Josefina Valdez"
                      }
                    ],
                    "greeting": "Hello, Aguirre Pittman! You have 4 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b32b7550a455add657",
                    "index": 4,
                    "guid": "4d479205-50f7-4450-b8de-fca018ed9ed6",
                    "isActive": false,
                    "balance": "$1,237.74",
                    "picture": "http://placehold.it/32x32",
                    "age": 29,
                    "eyeColor": "brown",
                    "name": "Linda Todd",
                    "gender": "female",
                    "company": "EARGO",
                    "email": "lindatodd@eargo.com",
                    "phone": "+1 (935) 458-2135",
                    "address": "220 Wortman Avenue, Troy, Alaska, 4176",
                    "about": "Qui exercitation adipisicing qui officia velit non minim sint reprehenderit in cupidatat consequat. Tempor duis deserunt fugiat adipisicing nulla voluptate exercitation enim sit. Sint laborum nisi sunt eiusmod consectetur mollit.\\r\\n",
                    "registered": "2017-01-24T06:16:47 -02:00",
                    "latitude": 9.690775,
                    "longitude": -163.158716,
                    "tags": [
                      "est",
                      "mollit",
                      "sunt",
                      "officia",
                      "veniam",
                      "velit",
                      "elit"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Maritza Holder"
                      },
                      {
                        "id": 1,
                        "name": "Paulette Paul"
                      },
                      {
                        "id": 2,
                        "name": "Anastasia Rodriguez"
                      }
                    ],
                    "greeting": "Hello, Linda Todd! You have 4 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b3cf431f57da512bb3",
                    "index": 5,
                    "guid": "15d3f431-7ff5-4e0d-bc6a-f193f1d311d6",
                    "isActive": true,
                    "balance": "$1,807.59",
                    "picture": "http://placehold.it/32x32",
                    "age": 34,
                    "eyeColor": "green",
                    "name": "Trudy Wooten",
                    "gender": "female",
                    "company": "BUZZOPIA",
                    "email": "trudywooten@buzzopia.com",
                    "phone": "+1 (874) 420-3915",
                    "address": "414 Pershing Loop, Yonah, Marshall Islands, 3699",
                    "about": "Consectetur minim ea aliquip exercitation. Do pariatur irure sunt aliquip occaecat dolor mollit proident aliqua veniam. Ex mollit non do voluptate culpa mollit qui laborum esse aliqua enim.\\r\\n",
                    "registered": "2015-07-17T07:01:03 -03:00",
                    "latitude": 88.764821,
                    "longitude": 168.985813,
                    "tags": [
                      "exercitation",
                      "qui",
                      "anim",
                      "eu",
                      "aliquip",
                      "id",
                      "magna"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Thelma Obrien"
                      },
                      {
                        "id": 1,
                        "name": "Cabrera Campbell"
                      },
                      {
                        "id": 2,
                        "name": "Mcfarland Harrington"
                      }
                    ],
                    "greeting": "Hello, Trudy Wooten! You have 2 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b3f8332f14e4220e7b",
                    "index": 6,
                    "guid": "6c5c4b4e-9796-406c-9ac5-a1961a189210",
                    "isActive": false,
                    "balance": "$1,576.99",
                    "picture": "http://placehold.it/32x32",
                    "age": 30,
                    "eyeColor": "blue",
                    "name": "Boyd Nixon",
                    "gender": "male",
                    "company": "UNQ",
                    "email": "boydnixon@unq.com",
                    "phone": "+1 (861) 544-3022",
                    "address": "513 Monroe Place, Detroit, Louisiana, 6877",
                    "about": "Eu duis ut velit et enim labore excepteur nisi sit id adipisicing incididunt excepteur. Ipsum culpa ut aliquip voluptate enim commodo irure excepteur culpa. Magna ut nulla culpa esse tempor amet nisi Lorem consequat adipisicing.\\r\\n",
                    "registered": "2016-01-29T03:01:18 -02:00",
                    "latitude": 23.463126,
                    "longitude": -152.959073,
                    "tags": [
                      "fugiat",
                      "aliqua",
                      "incididunt",
                      "sint",
                      "adipisicing",
                      "labore",
                      "aliquip"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Patrica Summers"
                      },
                      {
                        "id": 1,
                        "name": "Mandy Alvarez"
                      },
                      {
                        "id": 2,
                        "name": "Stone Owens"
                      }
                    ],
                    "greeting": "Hello, Boyd Nixon! You have 5 unread messages.",
                    "favoriteFruit": "strawberry"
                  },
                  {
                    "_id": "5b1fc6b331c55ff5d56d2c57",
                    "index": 7,
                    "guid": "2c94eaa5-e1e3-470c-88c7-8c2683240377",
                    "isActive": true,
                    "balance": "$2,555.93",
                    "picture": "http://placehold.it/32x32",
                    "age": 38,
                    "eyeColor": "blue",
                    "name": "Gallagher Calderon",
                    "gender": "male",
                    "company": "NIMON",
                    "email": "gallaghercalderon@nimon.com",
                    "phone": "+1 (948) 532-2857",
                    "address": "439 Stryker Court, Nanafalia, Arkansas, 1228",
                    "about": "Officia eu do exercitation officia ex Lorem minim ipsum duis. Eu enim sint laboris nulla irure consectetur fugiat occaecat incididunt cupidatat. Nisi Lorem occaecat tempor ipsum sint cupidatat id anim ea non occaecat excepteur elit cillum.\\r\\n",
                    "registered": "2016-01-07T06:49:14 -02:00",
                    "latitude": 26.159356,
                    "longitude": -45.713632,
                    "tags": [
                      "anim",
                      "officia",
                      "esse",
                      "cillum",
                      "cupidatat",
                      "est",
                      "incididunt"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "West Jimenez"
                      },
                      {
                        "id": 1,
                        "name": "Madden Hahn"
                      },
                      {
                        "id": 2,
                        "name": "Hicks Burnett"
                      }
                    ],
                    "greeting": "Hello, Gallagher Calderon! You have 7 unread messages.",
                    "favoriteFruit": "strawberry"
                  },
                  {
                    "_id": "5b1fc6b3d0459a2558d714b5",
                    "index": 8,
                    "guid": "78e02ac7-fc77-4528-8e9d-f48811c06c7e",
                    "isActive": false,
                    "balance": "$1,557.59",
                    "picture": "http://placehold.it/32x32",
                    "age": 36,
                    "eyeColor": "brown",
                    "name": "Sharon Johns",
                    "about": "Fugiat consectetur in magna id occaecat sit aliquip ea ea eiusmod nisi dolor veniam consectetur. Exercitation eiusmod ut ad in culpa ut reprehenderit aliqua Lorem magna enim adipisicing veniam aute. Eu quis pariatur irure nisi deserunt. Labore velit deserunt excepteur deserunt excepteur adipisicing sint ea voluptate dolor reprehenderit eiusmod Lorem. Exercitation est proident eiusmod consectetur dolor pariatur Lorem veniam do incididunt. Aliquip non pariatur mollit ut consectetur minim aliqua. Excepteur sunt cillum proident aliquip eiusmod amet ipsum Lorem esse ad.\\r\\n",
                    "gender": "female",
                    "company": "ACUMENTOR",
                    "email": "sharonjohns@acumentor.com",
                    "phone": "+1 (973) 409-3797",
                    "address": "695 Thames Street, Hinsdale, Connecticut, 479",
                    "registered": "2016-07-01T03:28:01 -03:00",
                    "latitude": 84.257902,
                    "longitude": 144.671579,
                    "tags": [
                      "do",
                      "do",
                      "deserunt",
                      "aute",
                      "do",
                      "ut",
                      "velit"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Lenora Cochran"
                      },
                      {
                        "id": 1,
                        "name": "Bolton Mathews"
                      },
                      {
                        "id": 2,
                        "name": "Walls Bridges"
                      }
                    ],
                    "greeting": "Hello, Sharon Johns! You have 3 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b3a94c8578e056b2c4",
                    "index": 9,
                    "guid": "f7b153b1-d7d2-480a-be75-b855a06bd231",
                    "isActive": true,
                    "balance": "$1,005.55",
                    "picture": "http://placehold.it/32x32",
                    "age": 25,
                    "eyeColor": "green",
                    "name": "Pate Yates",
                    "gender": "male",
                    "company": "GENMOM",
                    "email": "pateyates@genmom.com",
                    "phone": "+1 (944) 571-2086",
                    "address": "846 Hampton Place, Sultana, Rhode Island, 2728",
                    "about": "Ex magna veniam dolore cillum amet Lorem excepteur mollit excepteur ut nisi ex Lorem non. Labore culpa sint nostrud voluptate cupidatat aute nisi reprehenderit minim et culpa pariatur id nisi. Qui irure do cupidatat et sit culpa amet ullamco fugiat officia magna enim.\\r\\n",
                    "registered": "2017-01-27T10:01:13 -02:00",
                    "latitude": -88.097111,
                    "longitude": -10.476844,
                    "tags": [
                      "elit",
                      "do",
                      "sit",
                      "eiusmod",
                      "occaecat",
                      "veniam",
                      "nisi"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Robles Santos"
                      },
                      {
                        "id": 1,
                        "name": "Ortega Whitley"
                      },
                      {
                        "id": 2,
                        "name": "Eunice Osborne"
                      }
                    ],
                    "greeting": "Hello, Pate Yates! You have 7 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b350d78bedaf096052",
                    "index": 10,
                    "guid": "bf079cfe-3753-4641-a8b1-488227ffe157",
                    "isActive": false,
                    "balance": "$3,389.64",
                    "picture": "http://placehold.it/32x32",
                    "age": 21,
                    "eyeColor": "brown",
                    "name": "Shannon Whitney",
                    "gender": "male",
                    "company": "WEBIOTIC",
                    "email": "shannonwhitney@webiotic.com",
                    "phone": "+1 (893) 565-3758",
                    "address": "892 Herbert Street, Irwin, Mississippi, 3383",
                    "about": "Ipsum cupidatat veniam ipsum voluptate dolore. Eu et aute ipsum velit esse aliquip id anim veniam enim ullamco. Laboris sunt tempor laboris irure esse nulla eiusmod qui excepteur tempor do ullamco culpa labore. Incididunt non in nostrud consectetur mollit consequat irure.\\r\\n",
                    "registered": "2018-05-11T02:42:17 -03:00",
                    "latitude": -36.559857,
                    "longitude": -96.966372,
                    "tags": [
                      "eiusmod",
                      "est",
                      "aliqua",
                      "aliquip",
                      "culpa",
                      "consectetur",
                      "occaecat"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Kristi Fields"
                      },
                      {
                        "id": 1,
                        "name": "Avila Conner"
                      },
                      {
                        "id": 2,
                        "name": "Cleo Hurst"
                      }
                    ],
                    "greeting": "Hello, Shannon Whitney! You have 9 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b35c9b382e79bf04fe",
                    "index": 11,
                    "guid": "5cfe75d1-da1d-471c-a282-0ceaa5dbf0df",
                    "isActive": true,
                    "balance": "$1,648.26",
                    "picture": "http://placehold.it/32x32",
                    "age": 28,
                    "eyeColor": "green",
                    "name": "Genevieve Mullen",
                    "gender": "female",
                    "company": "COMVEYOR",
                    "email": "genevievemullen@comveyor.com",
                    "phone": "+1 (983) 439-3024",
                    "address": "575 Christopher Avenue, Wells, Colorado, 3721",
                    "about": "Eiusmod cillum irure sint cupidatat do culpa do in pariatur anim magna. Lorem duis amet non aliqua ut magna excepteur ullamco id culpa velit incididunt qui dolor. Duis est qui esse velit velit laborum cupidatat duis sunt qui est pariatur irure. Qui qui magna id laborum. Aute id exercitation reprehenderit sint id esse est sunt aliquip magna consequat.\\r\\n",
                    "registered": "2017-06-27T11:27:36 -03:00",
                    "latitude": -20.462091,
                    "longitude": 53.85973,
                    "tags": [
                      "nostrud",
                      "reprehenderit",
                      "consectetur",
                      "sint",
                      "voluptate",
                      "duis",
                      "est"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Marisol Webster"
                      },
                      {
                        "id": 1,
                        "name": "White Hicks"
                      },
                      {
                        "id": 2,
                        "name": "Mack Gibbs"
                      }
                    ],
                    "greeting": "Hello, Genevieve Mullen! You have 2 unread messages.",
                    "favoriteFruit": "strawberry"
                  },
                  {
                    "_id": "5b1fc6b3e175179c9cef38c1",
                    "index": 12,
                    "guid": "0d9e438b-9dbc-42ca-bdf2-c293728e8adc",
                    "isActive": true,
                    "balance": "$3,966.86",
                    "picture": "http://placehold.it/32x32",
                    "age": 35,
                    "eyeColor": "blue",
                    "name": "Benton Klein",
                    "gender": "male",
                    "company": "NIXELT",
                    "email": "bentonklein@nixelt.com",
                    "phone": "+1 (883) 584-2089",
                    "address": "724 Beekman Place, Stouchsburg, West Virginia, 5184",
                    "about": "Proident nisi elit consectetur consequat pariatur duis ullamco est esse sint sunt sint nostrud. Non non quis duis ea culpa Lorem ex minim. Nulla ad duis qui id commodo. Dolore sint anim laborum velit cillum exercitation culpa dolor in exercitation cupidatat est elit. Elit ipsum adipisicing reprehenderit dolor nisi duis ex duis magna incididunt incididunt mollit. Adipisicing veniam sunt culpa eiusmod tempor Lorem dolore ut adipisicing occaecat do sint proident tempor.\\r\\n",
                    "registered": "2016-01-07T05:33:42 -02:00",
                    "latitude": 53.145629,
                    "longitude": -54.119043,
                    "tags": [
                      "est",
                      "sit",
                      "amet",
                      "ea",
                      "consectetur",
                      "elit",
                      "aliqua"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Joyner Rosario"
                      },
                      {
                        "id": 1,
                        "name": "Marva Sweeney"
                      },
                      {
                        "id": 2,
                        "name": "Mona Boone"
                      }
                    ],
                    "greeting": "Hello, Benton Klein! You have 3 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b314366485d39e85ed",
                    "index": 13,
                    "guid": "5a977e03-6c60-427d-804d-14d3df422219",
                    "isActive": false,
                    "balance": "$2,586.02",
                    "picture": "http://placehold.it/32x32",
                    "age": 26,
                    "eyeColor": "brown",
                    "name": "Dollie Mueller",
                    "gender": "female",
                    "company": "QUILTIGEN",
                    "email": "dolliemueller@quiltigen.com",
                    "phone": "+1 (947) 543-3017",
                    "address": "626 Amboy Street, Convent, Indiana, 3304",
                    "about": "Cillum laboris aliqua deserunt ea dolore ullamco in esse ipsum ullamco aliqua. Exercitation tempor proident reprehenderit ipsum duis aute. Ut dolor excepteur deserunt nisi. Veniam amet ut aute anim ut aliqua excepteur voluptate deserunt velit culpa labore veniam nulla. Lorem consequat officia proident quis adipisicing culpa non duis ullamco pariatur qui dolore ex.\\r\\n",
                    "registered": "2017-03-14T10:17:50 -02:00",
                    "latitude": -51.779456,
                    "longitude": 63.511476,
                    "tags": [
                      "velit",
                      "excepteur",
                      "laborum",
                      "excepteur",
                      "ut",
                      "ex",
                      "aliqua"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Bender Kelly"
                      },
                      {
                        "id": 1,
                        "name": "Sanchez Madden"
                      },
                      {
                        "id": 2,
                        "name": "Clay Hoffman"
                      }
                    ],
                    "greeting": "Hello, Dollie Mueller! You have 6 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b31f9ce5cd76cc9e14",
                    "index": 14,
                    "guid": "1d4607c6-a7d3-4693-a4a6-b969e35e5651",
                    "isActive": false,
                    "balance": "$1,440.33",
                    "picture": "http://placehold.it/32x32",
                    "age": 27,
                    "eyeColor": "brown",
                    "name": "Baird Berry",
                    "gender": "male",
                    "company": "ACCIDENCY",
                    "email": "bairdberry@accidency.com",
                    "phone": "+1 (849) 451-2702",
                    "address": "464 Jodie Court, Hardyville, Florida, 3083",
                    "about": "Culpa nostrud exercitation nisi excepteur exercitation consequat quis elit ad ullamco. Ut laborum eu cillum officia id excepteur ullamco ex cupidatat sit eu elit amet. Pariatur dolore ipsum adipisicing aliqua elit Lorem aliqua ea. Incididunt aute in fugiat deserunt commodo sit anim. Nostrud dolore eiusmod nulla incididunt aliqua.\\r\\n",
                    "registered": "2017-03-24T01:31:56 -02:00",
                    "latitude": -10.966882,
                    "longitude": 43.470642,
                    "tags": [
                      "est",
                      "aute",
                      "deserunt",
                      "do",
                      "occaecat",
                      "magna",
                      "amet"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Valarie Avery"
                      },
                      {
                        "id": 1,
                        "name": "Tanner Matthews"
                      },
                      {
                        "id": 2,
                        "name": "Etta Irwin"
                      }
                    ],
                    "greeting": "Hello, Baird Berry! You have 7 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b375565a13ea216365",
                    "index": 15,
                    "guid": "fa0b45a1-34ef-4ec5-8f2a-3c4195215e0b",
                    "isActive": false,
                    "balance": "$2,098.35",
                    "picture": "http://placehold.it/32x32",
                    "age": 21,
                    "eyeColor": "brown",
                    "name": "Courtney Cross",
                    "gender": "female",
                    "company": "FIBEROX",
                    "email": "courtneycross@fiberox.com",
                    "phone": "+1 (950) 433-3147",
                    "address": "311 Fayette Street, Sanborn, New Jersey, 4875",
                    "about": "Voluptate labore nulla et ea elit. Aliquip labore cillum aliqua dolor ad ad sunt nisi. Sunt consectetur ad pariatur occaecat qui pariatur veniam. Laboris id quis laboris aliqua id velit officia consequat.\\r\\n",
                    "registered": "2015-07-10T12:25:58 -03:00",
                    "latitude": -46.388225,
                    "longitude": -153.147474,
                    "tags": [
                      "sunt",
                      "laboris",
                      "minim",
                      "nulla",
                      "veniam",
                      "sint",
                      "non"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Austin Spears"
                      },
                      {
                        "id": 1,
                        "name": "Rae Camacho"
                      },
                      {
                        "id": 2,
                        "name": "Harmon Livingston"
                      }
                    ],
                    "greeting": "Hello, Courtney Cross! You have 10 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b397fe1766769eb465",
                    "index": 16,
                    "guid": "c5f496fb-1e95-4531-9ae2-66c627ec3d3d",
                    "isActive": true,
                    "balance": "$1,347.10",
                    "picture": "http://placehold.it/32x32",
                    "age": 36,
                    "eyeColor": "brown",
                    "name": "Ayers Kerr",
                    "gender": "male",
                    "company": "CORPORANA",
                    "email": "ayerskerr@corporana.com",
                    "phone": "+1 (991) 489-2361",
                    "address": "596 Graham Avenue, Keyport, Delaware, 9139",
                    "about": "Culpa ad pariatur laborum cupidatat adipisicing occaecat consequat elit enim reprehenderit anim est reprehenderit. Consequat in elit proident mollit nulla. Ex enim excepteur ex labore pariatur velit. Laboris incididunt fugiat eu laboris cupidatat esse velit nulla pariatur ad pariatur esse reprehenderit.\\r\\n",
                    "registered": "2016-05-17T03:11:15 -03:00",
                    "latitude": 67.953667,
                    "longitude": 162.003308,
                    "tags": [
                      "non",
                      "irure",
                      "labore",
                      "minim",
                      "nostrud",
                      "ad",
                      "mollit"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Janet Hampton"
                      },
                      {
                        "id": 1,
                        "name": "Sonia Allen"
                      },
                      {
                        "id": 2,
                        "name": "Amie Roth"
                      }
                    ],
                    "greeting": "Hello, Ayers Kerr! You have 2 unread messages.",
                    "favoriteFruit": "apple"
                  },
                  {
                    "_id": "5b1fc6b3766fe246b3cae522",
                    "index": 17,
                    "guid": "753a3eb0-6bb8-4bbe-95cb-44d0715f6343",
                    "isActive": true,
                    "balance": "$3,816.99",
                    "picture": "http://placehold.it/32x32",
                    "age": 23,
                    "eyeColor": "green",
                    "name": "Hunter Vega",
                    "gender": "male",
                    "company": "NETERIA",
                    "email": "huntervega@neteria.com",
                    "phone": "+1 (880) 411-3805",
                    "address": "819 Ford Street, Cassel, Pennsylvania, 961",
                    "about": "Excepteur sit laboris magna cupidatat laborum amet aliqua aute. Mollit magna mollit quis exercitation sit. Enim exercitation id in ad elit cupidatat ullamco quis in in velit nisi proident elit. Aliquip consequat aliquip sint dolore consectetur cillum ad et incididunt occaecat laborum irure adipisicing. Consectetur ex sint culpa et laboris exercitation aliqua irure qui adipisicing et deserunt esse. Non duis pariatur sit voluptate ullamco. Elit eiusmod nisi magna cillum enim dolor magna deserunt sit dolore aliquip occaecat.\\r\\n",
                    "registered": "2016-10-22T03:09:17 -03:00",
                    "latitude": -41.888408,
                    "longitude": 111.835503,
                    "tags": [
                      "ea",
                      "et",
                      "duis",
                      "nisi",
                      "est",
                      "nostrud",
                      "nisi"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Rosetta Tyler"
                      },
                      {
                        "id": 1,
                        "name": "Parrish Swanson"
                      },
                      {
                        "id": 2,
                        "name": "Noreen Frazier"
                      }
                    ],
                    "greeting": "Hello, Hunter Vega! You have 8 unread messages.",
                    "favoriteFruit": "banana"
                  },
                  {
                    "_id": "5b1fc6b3fd585229aa056326",
                    "index": 18,
                    "guid": "25ece042-e6f7-433e-aafb-4e3f3fb79aaa",
                    "isActive": true,
                    "balance": "$3,418.60",
                    "picture": "http://placehold.it/32x32",
                    "age": 35,
                    "eyeColor": "green",
                    "name": "Sheri Payne",
                    "gender": "female",
                    "company": "TETAK",
                    "email": "sheripayne@tetak.com",
                    "phone": "+1 (824) 530-2349",
                    "address": "278 Macon Street, Brutus, Virginia, 892",
                    "about": "Consequat laboris ipsum incididunt proident nostrud reprehenderit fugiat velit commodo eiusmod enim in cupidatat ullamco. Non ex duis est mollit dolor officia magna excepteur aliquip in eiusmod Lorem voluptate. Nisi duis in et est qui ut et. Adipisicing quis velit qui officia duis id. Voluptate veniam irure qui ad exercitation esse sunt. Aute nulla ipsum aliquip quis sit non deserunt amet aliqua incididunt adipisicing eiusmod.\\r\\n",
                    "registered": "2016-06-14T05:30:16 -03:00",
                    "latitude": -14.633416,
                    "longitude": 48.022597,
                    "tags": [
                      "officia",
                      "in",
                      "eiusmod",
                      "dolore",
                      "sunt",
                      "proident",
                      "irure"
                    ],
                    "friends": [
                      {
                        "id": 0,
                        "name": "Beulah Dyer"
                      },
                      {
                        "id": 1,
                        "name": "Moon Davis"
                      },
                      {
                        "id": 2,
                        "name": "Fox James"
                      }
                    ],
                    "greeting": "Hello, Sheri Payne! You have 8 unread messages.",
                    "favoriteFruit": "apple"
                  }
                ]
                """;
        JSONCompare.compare(expected, actual).assertNotMatches();
    }

    @Test
    public void testVeryLargeJsonCompare() throws IOException {
        JSONCompare.compare(readFromRelativePath("bigJsons/expectedLargeJson.json"), readFromRelativePath("bigJsons/actualLargeJson.json")).assertMatches();
    }

    @Test
    public void testVeryLargeJsonCompare_negative() throws IOException {
        JSONCompare.compare(readFromRelativePath("bigJsons/expectedWrongLargeJson.json"), readFromRelativePath("bigJsons/actualLargeJson.json")).assertNotMatches();

    }

    private static String readFromRelativePath(String relativeFilePath) throws IOException {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(relativeFilePath);
             ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            if (is == null) {
                throw new IOException("File " + relativeFilePath + " not found");
            }
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            return result.toString(StandardCharsets.UTF_8.name());
        }
    }

}
