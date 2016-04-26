# Super Simple Test
An application build using spring-boot to accelerate the develop of this simple business application. The aim of this project is to show how to develop a full layered architecture with some use cases in a Behaviour Driven Development style.
## Arhictecture layers
The architecture is built with 4 layers: client (here rest controllers), facade, service and DAO whose main features are:
- Client: consume the business logic.
- Facade: Expose the use cases. Non-implemented responsibilities should be secure the app, log the requests and their responses, coordinate services, etc.
- Service: Just implement only the business logic in a reusable way.
- DAO: Access data.
- "Memory": This application has an unique class that stores information as long as you don't shutdown it.

Here the services are layered for helping our development and to ensure the quality of our architecture/application. But it should be required to also define the message, may be, with command and/or data transfer object patterns-

## Behaviour Driven Development
Unit tests are written following BDD principles (user stories are not described). Here the clear responsibilities separation of the layers help to develop the tests. Within the tests you will find both mocks and stubs that will call the actual implementation or not, just to show the differences.
## Sping aspect
In addition, an AOP has been created to log every use case request. It is not the aim of this project to do an accurate log but I would want to show how important is to layer your architecture and separate concepts.
## How to execute it?
Just build the application by executing:
`mvn clean install`
Once the maven process is finished, you could execute the application running:
`java -jar target/jpmorgan-super-simple-0.1.0-SNAPSHOT.jar`
The folling table lists the available REST Services
| Path                      | Description                                           | Type | Paramters                                  |
|---------------------------|-------------------------------------------------------|------|---------------------------------------------------|
| /stock/calculate-dividend-yield/{id} | Calculates the dividend yiel for a given stock        | GET  | id=[ALE, GIN, POP, JOE, TEA]                  |
| /stock/price-earning-ratio/{id}      | Calculates the P/E ratio for a given stock            | GET  | id=[ALE, GIN, POP, JOE, TEA]                  |
| /stock/ticker-price/{id}             | Calculates the ticker price (15min) for a given stock | GET  | id=[ALE, GIN, POP, JOE, TEA]                  |
| /stock/stock-price/{id}              | Calculates the stock price for a given stock          | GET  | id=[ALE, GIN, POP, JOE, TEA]                  |
| /stock/gbce                          | Calculates the GBCE All Share Index                   | GET  | id=[ALE, GIN, POP, JOE, TEA]                  |
| /trade/buy-stocks                    | Calculates the GBCE All Share Index                   | POST | id=[ALE, GIN, POP, JOE, TEA], price, quantity |
| /trade/sell-stocks                   | Calculates the GBCE All Share Index                   | POST | id=[ALE, GIN, POP, JOE, TEA], price, quantity |


