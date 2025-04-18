# Currency Exchange Demo

This is a Spring Boot application that provides a simple RESTful API for creating a multi-currency account and ordering
currency exchanges.

## Features

- [x] Aplikacja posiada REST API pozwalające na założenie konta walutowego.
- [x] Przy zakładaniu konta wymagane jest podanie początkowego salda konta w PLN.
- [x] Aplikacja przy zakładaniu konta wymaga od użytkownika podania imienia i nazwiska.
- [x] Aplikacja przy zakładaniu konta generuje identyfikator konta, który powinien być używany przy wywoływaniu dalszych
  metod API.
- [x] Aplikacja powinna udostępnić REST API do wymiany pieniędzy w parze PLN<->USD (czyli PLN na USD oraz USD na PLN),
  a aktualny kurs wymiany pobrać z publicznego API NBP (http://api.nbp.pl/).
- [x] Aplikacja powinna udostępnić REST API do pobrania danych o koncie i jego aktualnego stanu w PLN i USD.

### Założenia

- Osoba może założyć wiele kont walutowych - brak walidacji danych klienta poza ich obecnością
- Konwersja walut tylko w ramach jednego konta
- Identyfikator numeryczny - 10 znaków
- Kursy zwracane przez API NBP są stałe - brak sprawdzania zmiany kursu oraz brak potwierdzenia zmian
- Nie ma możliwości kredytowania
- Wykorzystano eventy do zapisu dodatkowych danych (historia transakcji)

### Propozycje

- [ ] Cache odpowiedzi z API NBP resetowany o północy - NBP nie udostępnia kursów w czasie rzeczywistym
- [ ] API zwracające wynik konwersji i kurs
- [ ] API do potwierdzenia wymiany
- [ ] Izolacja testów integracyjnych
- [ ] Logi
- [ ] Więcej indywidualnych/szczegółowych wyjątków
- [ ] Zapis historii konta - utworzenie, dodanie waluty, zmiana ustawień, itp.
- [ ] `equals` i `hashCode` biorące pod uwagę porównywanie `BigDecimal` - ewentualnie jakaś libka

## Getting Started

### Prerequisites

- Java 21 or higher
- [Docker](https://docs.docker.com/desktop/) environment

### Building the Application

Clone the repository and navigate to its main directory:

```bash
git clone git@github.com:cheeriosis/currency-exchange.git
cd currency-exchange
```

Build the application (use `-x` flag to skip tests):

```bash
./gradlew build -x test
```

### Running the application

The application utilizes `spring-boot-docker-compose` module, meaning the database defined in the `compose.yaml`
file will start and stop automatically. Please make sure your Docker environment is running and that port `5432` is not
taken by another app.

Execute:

```bash
./gradlew bootRun
```

to start the application. The API should be accessible via Swagger UI
at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

## Testing

Run the tests using the following command:

```bash
./gradlew test
```

[Testcontainers](https://java.testcontainers.org/) are used for integration tests, that also requires that your Docker
is running.

## Monitoring and Logging

This application uses [Spring Boot Actuator](https://docs.spring.io/spring-boot/reference/actuator/index.html) for
monitoring and logging. You can access the actuator endpoints
at [http://localhost:8080/actuator](http://localhost:8080/actuator).
