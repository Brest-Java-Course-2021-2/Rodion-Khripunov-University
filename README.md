# Brest Java Course 2021 2 Rodion Khripunov

This is sample "University" Spring boot web application with REST.

## Requirements

* JDK 11
* Apache Maven

# Build application:

**Build using terminal:**

Use this if you have *Maven* installed:

```
mvn clean install
```

OR

If *Maven* is not installed:

```
./mvnw clean install
```



**To start Rest server:**

Starting rest application on *localhost:8088*:

```
java -jar ./rest-app/target/rest-app-1.0-SNAPSHOT.jar
```

**To start WEB server:**

Starting web application on *localhost:8080*:

```
java -jar ./web-app/target/web-app-1.0-SNAPSHOT.jar
```

# Available REST endpoints

### version

Returns the application version:

```
curl --request GET 'http://localhost:8088/version'
```

### university-dto

Returns a list of university dtos:

```
curl --request GET 'http://localhost:8088/university_dto'
```

## universities

### findAll

Returns a list of universities:

```
curl --request GET 'http://localhost:8088/universities'
```

### findById

Returns the university by `id`:

```
curl --request GET 'http://localhost:8088/universities/1'
```

### create

Creates the university with provided fields and returns an id:

```
curl --request POST 'http://localhost:8088/universities' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "universityName": "BGU"
}'
```

### update

Updates the university with provided fields:

```
curl --request PUT 'http://localhost:8088/universities' \
--header 'Content-Type: application/json' \
--data-raw '{
    "universityId": 4,
    "universityName": "BGU"
}'
```

### delete

Removes the university by `id`:

```
curl --request DELETE 'http://localhost:8088/universities/4'
```

## student-dto

### student-dto

Returns a list of student dtos:

```
curl --request GET 'http://localhost:8088/student_dto'
```

### students-dto filtered

Returns a list of filtered by `enrollmentDate` student dtos:

```
curl --request GET 'http://localhost:8088/student_dto/filter?startDate=2019-07-20&endDate=2019-07-27'
```

> `startDate` and `endDate` are representing a range of dates

## students

### findAll

Returns a list of students:

```
curl --request GET 'http://localhost:8088/students'
```

### findById

Returns the student by `id`:

```
curl --request GET 'http://localhost:8088/students/1'
```

### create

Creates the student with provided fields and returns an id:

```
curl --request POST 'http://localhost:8088/students' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "firstName": "Ivan",
    "lastName": "Ivanov",
    "enrollmentDate": "2020-01-01",
    "email": "example@mail.org",
    "course": 4,
    "rating": 5,
    "universityId": 1
}'
```

### update

Updates the student with provided fields:

```
curl --request PUT 'http://localhost:8088/students' \
--header 'Content-Type: application/json' \
--data-raw '{
    "studentId": 6,
    "firstName": "Ivan",
    "lastName": "Ivanov",
    "enrollmentDate": "2020-01-01",
    "email": "updatedexample@mail.org",
    "course": 4,
    "rating": 7,
    "universityId": 2
}'
```

### delete

Removes the students by `id`:

```
curl --request DELETE 'http://localhost:8088/students/6'
```