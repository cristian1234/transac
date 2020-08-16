# Money Transactions


### Prerequisites to build
* java 11 sdk
* maven 3.6.3

### How to build
* in command line run: mvn clean install

### How to change service port
* edit property server.port=8081 in the src/main/resources/application.properties config file.

### How to run application
* in command line run: java -jar transac-0.0.1-SNAPSHOT.jar com.paxos.test.transac.TransacApplication

### Api documentation
* Endpoints:

* Get current account balance: GET to http://localhost/8081/
Example Response:
{
    "balance": 4.00
}

* Get historical transactions: GET to http://localhost/8081/transactions
Example Response:
[
    {
        "id": 1,
        "amount": 1.00,
        "effectiveDate": "2020-08-16T22:00:22.841085Z",
        "type": "credit"
    },
    {
        "id": 2,
        "amount": 1.00,
        "effectiveDate": "2020-08-16T22:00:25.436503Z",
        "type": "credit"
    },
    {
        "id": 3,
        "amount": 1.00,
        "effectiveDate": "2020-08-16T22:00:25.802523Z",
        "type": "credit"
    }
]
* Get a transaction by id, for example id=2: GET to http://localhost/8081/transactions/2
Example Response:
    {
        "id": 2,
        "amount": 1.00,
        "effectiveDate": "2020-08-16T22:00:25.436503Z",
        "type": "credit"
    }


* Commit a new debit transaction: POST to http://localhost/8081/transactions with
{
  "type": "debit",
  "amount": 20.3
}



* Commit a new credit transaction: POST to http://localhost/8081/transactions with
{
  "type": "credit",
  "amount": 40.1
} 


