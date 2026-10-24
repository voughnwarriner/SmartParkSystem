# SmartPark API - Test Scenarios & Sample Data

This guide provides multiple test scenarios and sample data for comprehensive testing of the SmartPark API.

## Table of Contents
1. [Authentication Tests](#authentication-tests)
2. [Vehicle Registration Tests](#vehicle-registration-tests)
3. [Parking Lot Tests](#parking-lot-tests)
4. [Check-In/Check-Out Scenarios](#check-incheck-out-scenarios)
5. [Error/Edge Case Tests](#erroredge-case-tests)

---

## Authentication Tests

### Scenario 1: Successful Login (Valid Credentials)
**Endpoint:** `POST /api/auth/login`

**Request:**
```json
{
  "username": "admin",
  "password": "SmartPark123!"
}
```

**Expected Response:** `200 OK`
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 900
}
```

---

### Scenario 2: Failed Login (Invalid Username)
**Endpoint:** `POST /api/auth/login`

**Request:**
```json
{
  "username": "wronguser",
  "password": "SmartPark123!"
}
```

**Expected Response:** `401 Unauthorized`
```json
{
  "error": "Unauthorized",
  "message": "Invalid username or password"
}
```

---

### Scenario 3: Failed Login (Invalid Password)
**Endpoint:** `POST /api/auth/login`

**Request:**
```json
{
  "username": "admin",
  "password": "WrongPassword123!"
}
```

**Expected Response:** `401 Unauthorized`

---

### Scenario 4: Failed Login (Empty Credentials)
**Endpoint:** `POST /api/auth/login`

**Request:**
```json
{
  "username": "",
  "password": ""
}
```

**Expected Response:** `400 Bad Request`

---

## Vehicle Registration Tests

### Scenario 1: Register CAR (Valid)
**Endpoint:** `POST /api/vehicles`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "NEW-1234",
  "type": "CAR",
  "ownerName": "Alex Santos"
}
```

**Expected Response:** `201 Created`
```json
{
  "licensePlate": "NEW-1234",
  "type": "CAR",
  "ownerName": "Alex Santos"
}
```

---

### Scenario 2: Register MOTORCYCLE (Valid)
**Endpoint:** `POST /api/vehicles`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "MOTO-002",
  "type": "MOTORCYCLE",
  "ownerName": "Maria Garcia"
}
```

**Expected Response:** `201 Created`

---

### Scenario 3: Register TRUCK (Valid)
**Endpoint:** `POST /api/vehicles`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "TRK-5001",
  "type": "TRUCK",
  "ownerName": "Roberto Cruz"
}
```

**Expected Response:** `201 Created`

---

### Scenario 4: Register with INVALID Vehicle Type
**Endpoint:** `POST /api/vehicles`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "INVALID-001",
  "type": "BICYCLE",
  "ownerName": "John Doe"
}
```

**Expected Response:** `400 Bad Request`
```json
{
  "error": "Bad Request",
  "message": "Invalid vehicle type. Must be MOTORCYCLE, CAR, or TRUCK"
}
```

---

### Scenario 5: Register with DUPLICATE License Plate
**Endpoint:** `POST /api/vehicles`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "ABC-1234",
  "type": "CAR",
  "ownerName": "Duplicate Owner"
}
```

**Expected Response:** `409 Conflict`
```json
{
  "error": "Conflict",
  "message": "Vehicle with license plate ABC-1234 already exists"
}
```

---

### Scenario 6: Register with INVALID License Plate Format
**Endpoint:** `POST /api/vehicles`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "INVALID@#$",
  "type": "CAR",
  "ownerName": "John Doe"
}
```

**Expected Response:** `400 Bad Request`

---

### Scenario 7: Register with EMPTY Owner Name
**Endpoint:** `POST /api/vehicles`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "EMPTY-001",
  "type": "CAR",
  "ownerName": ""
}
```

**Expected Response:** `400 Bad Request`

---

### Scenario 8: Get Existing Vehicle
**Endpoint:** `GET /api/vehicles/ABC-1234`
**Auth:** Required

**Expected Response:** `200 OK`
```json
{
  "licensePlate": "ABC-1234",
  "type": "CAR",
  "ownerName": "John Doe"
}
```

---

### Scenario 9: Get Non-Existent Vehicle
**Endpoint:** `GET /api/vehicles/NONEXIST-999`
**Auth:** Required

**Expected Response:** `404 Not Found`

---

### Scenario 10: List All Vehicles
**Endpoint:** `GET /api/vehicles`
**Auth:** Required

**Expected Response:** `200 OK`
```json
[
  {
    "licensePlate": "ABC-1234",
    "type": "CAR",
    "ownerName": "John Doe"
  },
  {
    "licensePlate": "MOTO-001",
    "type": "MOTORCYCLE",
    "ownerName": "Jane Smith"
  },
  {
    "licensePlate": "TRK-2025",
    "type": "TRUCK",
    "ownerName": "Bob Johnson"
  }
]
```

---

## Parking Lot Tests

### Scenario 1: Create New Parking Lot
**Endpoint:** `POST /api/lots`
**Auth:** Required

**Request:**
```json
{
  "lotId": "LOT-003",
  "location": "Quezon City",
  "capacity": 25
}
```

**Expected Response:** `201 Created`

---

### Scenario 2: Create Lot with DUPLICATE ID
**Endpoint:** `POST /api/lots`
**Auth:** Required

**Request:**
```json
{
  "lotId": "LOT-001",
  "location": "Duplicate Location",
  "capacity": 50
}
```

**Expected Response:** `409 Conflict`

---

### Scenario 3: Create Lot with INVALID Capacity
**Endpoint:** `POST /api/lots`
**Auth:** Required

**Request:**
```json
{
  "lotId": "LOT-004",
  "location": "Invalid Capacity",
  "capacity": 0
}
```

**Expected Response:** `400 Bad Request`

---

### Scenario 4: List All Parking Lots
**Endpoint:** `GET /api/lots`
**Auth:** Required

**Expected Response:** `200 OK`
```json
[
  {
    "lotId": "LOT-001",
    "location": "Makati CBD",
    "capacity": 20,
    "occupiedSpaces": 0,
    "availableSpaces": 20
  },
  {
    "lotId": "LOT-002",
    "location": "BGC Taguig",
    "capacity": 10,
    "occupiedSpaces": 0,
    "availableSpaces": 10
  }
]
```

---

### Scenario 5: Get Specific Parking Lot
**Endpoint:** `GET /api/lots/LOT-001`
**Auth:** Required

**Expected Response:** `200 OK`
```json
{
  "lotId": "LOT-001",
  "location": "Makati CBD",
  "capacity": 20,
  "occupiedSpaces": 0,
  "availableSpaces": 20
}
```

---

### Scenario 6: Get Non-Existent Lot
**Endpoint:** `GET /api/lots/LOT-999`
**Auth:** Required

**Expected Response:** `404 Not Found`

---

### Scenario 7: Check Lot Occupancy
**Endpoint:** `GET /api/lots/LOT-001/occupancy`
**Auth:** Required

**Expected Response:** `200 OK`
```json
{
  "lotId": "LOT-001",
  "totalCapacity": 20,
  "occupiedSpaces": 2,
  "availableSpaces": 18,
  "occupancyPercentage": 10.0
}
```

---

## Check-In/Check-Out Scenarios

### Scenario 1: Check-In Vehicle to Available Lot (CAR)
**Endpoint:** `POST /api/lots/LOT-001/check-in`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "ABC-1234"
}
```

**Expected Response:** `200 OK`
```json
{
  "sessionId": "session-uuid-12345",
  "vehicleLicensePlate": "ABC-1234",
  "lotId": "LOT-001",
  "checkInTime": "2026-10-24T17:04:32.799+08:00",
  "status": "ACTIVE"
}
```

---

### Scenario 2: Check-In MOTORCYCLE
**Endpoint:** `POST /api/lots/LOT-001/check-in`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "MOTO-001"
}
```

**Expected Response:** `200 OK`

---

### Scenario 3: Check-In TRUCK
**Endpoint:** `POST /api/lots/LOT-001/check-in`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "TRK-2025"
}
```

**Expected Response:** `200 OK`

---

### Scenario 4: Check-In Vehicle Already Parked
**Endpoint:** `POST /api/lots/LOT-001/check-in`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "ABC-1234"
}
```

**Expected Response:** `409 Conflict`
```json
{
  "error": "Conflict",
  "message": "Vehicle ABC-1234 is already parked in LOT-001"
}
```

---

### Scenario 5: Check-In to FULL Lot
**Endpoint:** `POST /api/lots/LOT-002/check-in` (LOT-002 has capacity 10, assume full)
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "NEW-5678"
}
```

**Expected Response:** `409 Conflict`
```json
{
  "error": "Conflict",
  "message": "Parking lot LOT-002 is at full capacity"
}
```

---

### Scenario 6: Check-In Non-Existent Vehicle
**Endpoint:** `POST /api/lots/LOT-001/check-in`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "NONEXIST-999"
}
```

**Expected Response:** `404 Not Found`

---

### Scenario 7: Check-Out CAR (Base Fee - Within 3 Hours)
**Endpoint:** `POST /api/lots/LOT-001/check-out`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "ABC-1234"
}
```

**Expected Response:** `200 OK`
```json
{
  "sessionId": "session-uuid-12345",
  "vehicleLicensePlate": "ABC-1234",
  "lotId": "LOT-001",
  "checkInTime": "2026-10-24T17:00:00.000+08:00",
  "checkOutTime": "2026-10-24T17:04:32.799+08:00",
  "durationMinutes": 4.533,
  "fee": 60.00,
  "status": "CHECKED_OUT"
}
```

---

### Scenario 8: Check-Out MOTORCYCLE (Base Fee)
**Endpoint:** `POST /api/lots/LOT-001/check-out`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "MOTO-001"
}
```

**Expected Response:** `200 OK`
```json
{
  "fee": 30.00
}
```

---

### Scenario 9: Check-Out TRUCK (Base Fee)
**Endpoint:** `POST /api/lots/LOT-001/check-out`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "TRK-2025"
}
```

**Expected Response:** `200 OK`
```json
{
  "fee": 120.00
}
```

---

### Scenario 10: Check-Out Vehicle with EXTENDED Duration (4 Hours)
**Endpoint:** `POST /api/lots/LOT-001/check-out`
**Auth:** Required
**Note:** Vehicle must be checked in for 4 hours

**Expected Response:** `200 OK`
```json
{
  "durationMinutes": 240,
  "fee": 80.00,
  "message": "CAR: Base fee 60.00 + 1 additional hour (20.00) = 80.00"
}
```

---

### Scenario 11: Check-Out Vehicle NOT in Lot
**Endpoint:** `POST /api/lots/LOT-001/check-out`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "NOT-PARKED"
}
```

**Expected Response:** `404 Not Found`
```json
{
  "error": "Not Found",
  "message": "No active parking session found for vehicle NOT-PARKED in lot LOT-001"
}
```

---

### Scenario 12: List Vehicles Currently Parked in Lot
**Endpoint:** `GET /api/lots/LOT-001/vehicles`
**Auth:** Required

**Expected Response:** `200 OK`
```json
[
  {
    "licensePlate": "ABC-1234",
    "type": "CAR",
    "ownerName": "John Doe",
    "checkInTime": "2026-10-24T17:00:00.000+08:00"
  },
  {
    "licensePlate": "MOTO-001",
    "type": "MOTORCYCLE",
    "ownerName": "Jane Smith",
    "checkInTime": "2026-10-24T16:55:00.000+08:00"
  }
]
```

---

## Error/Edge Case Tests

### Scenario 1: Request WITHOUT Authorization Header
**Endpoint:** `GET /api/vehicles`
**Auth:** None (Missing)

**Expected Response:** `401 Unauthorized`
```json
{
  "error": "Unauthorized",
  "message": "Missing or invalid authorization token"
}
```

---

### Scenario 2: Request with EXPIRED Token
**Endpoint:** `GET /api/vehicles`
**Auth:** Bearer (expired-token-here)

**Expected Response:** `401 Unauthorized`
```json
{
  "error": "Unauthorized",
  "message": "Token has expired"
}
```

---

### Scenario 3: Request with INVALID Token Format
**Endpoint:** `GET /api/vehicles`
**Auth:** Bearer InvalidTokenFormat123

**Expected Response:** `401 Unauthorized`

---

### Scenario 4: Malformed JSON Request
**Endpoint:** `POST /api/vehicles`
**Auth:** Required

**Request (Invalid JSON):**
```json
{
  "licensePlate": "TEST-001"
  "type": "CAR"
}
```

**Expected Response:** `400 Bad Request`

---

### Scenario 5: Missing Required Fields
**Endpoint:** `POST /api/vehicles`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "TEST-001"
}
```

**Expected Response:** `400 Bad Request`

---

### Scenario 6: Invalid Data Types
**Endpoint:** `POST /api/lots`
**Auth:** Required

**Request:**
```json
{
  "lotId": "LOT-005",
  "location": "Test",
  "capacity": "not-a-number"
}
```

**Expected Response:** `400 Bad Request`

---

### Scenario 7: Check-Out Immediately After Check-In
**Endpoint:** `POST /api/lots/LOT-001/check-out`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "ABC-1234"
}
```

**Expected Response:** `200 OK`
```json
{
  "durationMinutes": 0.05,
  "fee": 30.00,
  "message": "Minimum fee applied for CAR"
}
```

---

### Scenario 8: Concurrent Check-Ins (Race Condition Test)
**Endpoint:** `POST /api/lots/LOT-001/check-in`
**Auth:** Required

**Note:** Send two simultaneous requests for the same vehicle

**First Request:**
```json
{
  "licensePlate": "RACE-001"
}
```

**Second Request (Simultaneous):**
```json
{
  "licensePlate": "RACE-001"
}
```

**Expected Response:** One succeeds (200), other fails (409 Conflict)

---

### Scenario 9: Check-In to Non-Existent Lot
**Endpoint:** `POST /api/lots/LOT-999/check-in`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "ABC-1234"
}
```

**Expected Response:** `404 Not Found`

---

### Scenario 10: Very Long License Plate
**Endpoint:** `POST /api/vehicles`
**Auth:** Required

**Request:**
```json
{
  "licensePlate": "VERY-LONG-LICENSE-PLATE-1234567890",
  "type": "CAR",
  "ownerName": "John Doe"
}
```

**Expected Response:** `400 Bad Request` or `201 Created` (depending on validation)

---

## Pricing Calculation Examples

### CAR Pricing
- **Base Fee (0-3 hours):** PHP 60.00
- **Additional (per hour after 3 hours):** PHP 20.00
- **5 hours total:** 60.00 + (2 × 20.00) = **PHP 100.00**

### MOTORCYCLE Pricing
- **Base Fee (0-3 hours):** PHP 30.00
- **Additional (per hour after 3 hours):** PHP 10.00
- **4 hours total:** 30.00 + (1 × 10.00) = **PHP 40.00**

### TRUCK Pricing
- **Base Fee (0-3 hours):** PHP 120.00
- **Additional (per hour after 3 hours):** PHP 40.00
- **6 hours total:** 120.00 + (3 × 40.00) = **PHP 240.00**

---

## Test Data Summary

### Preloaded Vehicles
```
ABC-1234    | CAR        | John Doe
MOTO-001    | MOTORCYCLE | Jane Smith
TRK-2025    | TRUCK      | Bob Johnson
```

### Preloaded Lots
```
LOT-001     | Makati CBD      | Capacity: 20
LOT-002     | BGC Taguig      | Capacity: 10
```

### Test Credentials
```
Username: admin
Password: SmartPark123!
```

---

## How to Use This Guide

1. **Start with Authentication** - Get a valid token
2. **Test Vehicle Operations** - Register, list, and retrieve vehicles
3. **Test Lot Operations** - Create and manage parking lots
4. **Test Parking Sessions** - Check-in and check-out vehicles
5. **Test Error Cases** - Verify proper error handling
6. **Test Edge Cases** - Verify boundary conditions

Each scenario includes the expected HTTP status code and response format.
