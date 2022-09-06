@Regressivo
Feature: Testes de apresentação

  Scenario: Get all users
    Given faço um get para "/users"
    Then recebo a response com status 200 com os dados
      | meta                 | {matcher}notNullValue |
      | meta.pagination.page | {number}1             |
      | data                 | {matcher}notNullValue |

  Scenario: CRUD
    Given utilizo os headers "COM_AUTH"
    And adiciono o body do tipo "BodyPost" alterando os dados
    |name|Joao|
    When faço um post para "/users"
    Then recebo a response com status 201 com os dados
      | meta        | {matcher}nullValue    |
      | data.id     | {matcher}notNullValue |
      | data.name   | Joao            |
      | data.gender | male                  |
      | data.status | active                |
    And salvo os dados da response
      | idUsuarioCriado | data.id |
    And limpo o body adicionado anteriormente
    And adiciono os pathParams
      | id | {storage}idUsuarioCriado |
    When faço um get para "/users/{id}"
    Then recebo a response com status 200 com os dados
      | meta        | {matcher}nullValue       |
      | data.id     | {storage}idUsuarioCriado |
      | data.name   | Joao               |
      | data.gender | male                     |
      | data.status | active                   |
    And adiciono o body do tipo "BodyPut" alterando os dados
      | name   | Cleber |
      | status | active  |
    When faço um put para "/users/{id}"
    Then recebo a response com status 200 com os dados
      | meta        | {matcher}nullValue       |
      | data.id     | {storage}idUsuarioCriado |
      | data.name   | Cleber                  |
      | data.email  | {matcher}notNullValue    |
      | data.gender | male                     |
      | data.status | active                   |
    And salvo os dados da response
      | email | data.email |
    And limpo o body adicionado anteriormente
    When faço um get para "/users/{id}"
    Then recebo a response com status 200 com os dados
      | meta        | {matcher}nullValue       |
      | data.id     | {storage}idUsuarioCriado |
      | data.name   | Cleber                  |
      | data.email  | {storage}email           |
      | data.gender | male                     |
      | data.status | active                   |
    When faço um delete para "/users/{id}"
    Then recebo a response com status 204
    When faço um get para "/users/{id}"
    Then recebo a response com status 404 com os dados
      | meta         | {matcher}nullValue |
      | data.message | Resource not found |
