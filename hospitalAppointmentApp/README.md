# hospitalAppointmentApp

## How to run
Goto CLI(windows/linux)  
CD into hospitalAppointmentApp folder  
Run __`./mvnw spring-boot:run`__, Spring boot application will start on port 9090 __`http://localhost:9090/`__  

## API  
### Create an appointment  
    POST http://localhost:9090/appointment 
    BODY - {"fname":"John","lname":"Doe","dateOfBirth":"2010-12-12","appointmentDate":"2020-12-12T10:00"} 

### Retrieve an appointment
    GET http://localhost:9090/appointment/{confirmationId}
