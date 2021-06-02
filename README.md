# Arch System's Authentication Project

This project made for authentication (get Bearer token, and validate it) in arch system
infrastructure ([Project file change](https://github.com/oas1s/3rd_course_back)
, [Project frontend](https://github.com/AekoArray/arch-front)
, [Project console client](https://github.com/Gerrytty/systems-architecture-client) ).

# Requirements

* git (or you can download it manually)
* java
* maven
* postgres (or you can try another relational database supported with spring jpa)

## Installation

Clone this repository (or download it manually)

`git clone https://github.com/ARManakhov/3rd_course_auth`

Move to cloned repository

`cd 3rd_course_auth`

Compile project with maven

`mvn clean package`

Then you can run compiled jar file located in **target** folder

`java -jar auth-0.0.1-SNAPSHOT.jar`

## Usage

Application will start on port 8081 with next endpoints:

* POST /sign_in
    * body params:
        * username: String
        * password: String
    * headers:
        * Content-type: String (mime-type of request data)
    * response:
        * username: String
        * token: String (Bearer token, that can be used in validation)

* POST /sign_up
    * body params
        * username: String
        * email: String
        * password: String
    * headers:
        * Content-type: String (mime-type of request data)
    * response:
        * username: String
        * token: String (Bearer token, that can be used in validation)

* POST /valid
    * body is empty
    * headers:
        * Authorization: String (Bearer token)
    * response:
        * success: Boolean (status of request)
        * message: String (on success false contains error message)
        * result: String (on success true contains payload)

## Tests

For running tests use maven package manager

`mvn test`

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## Special Thanks

[Mihaylova Yuliya](https://github.com/Gerrytty) for reminding me about this part of project.

The rest of our command ([Mihaylova Yuliya](https://github.com/Gerrytty), [Azat](https://github.com/oas1s)
, [AekoArray](https://github.com/AekoArray), [DinoBambino](https://github.com/DinoBambino69)
, [amzn_22](https://github.com/amzn22))

## Warranties or conditions

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "
AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.