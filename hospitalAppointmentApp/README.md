# hospitalAppointmentApp

## How to run
1. Goto CLI(windows/linux)  
2. CD into hospitalAppointmentApp folder  
3. Run __`./mvnw spring-boot:run`__, Spring boot application will start on port 9090 __`http://localhost:9090/`__  

## API  
### Create an appointment  
    POST http://localhost:9090/appointment 
    
    Sample Request Body : {"fname":"John","lname":"Doe","dateOfBirth":"2010-12-12","appointmentDate":"2020-12-12T10:00"} 

### Retrieve an appointment
    GET http://localhost:9090/appointment/{confirmationId}
