# SmartPark Comprehensive Postman Collection Guide

## Overview
The `SmartPark_Comprehensive.postman_collection.json` contains **33 API endpoints** organized into 5 logical test folders, covering all functionality, error cases, and edge scenarios for the SmartPark parking management system.

## Quick Start

### Step 1: Import Collection
1. Open Postman
2. Click **Import** → **Upload Files**
3. Select `SmartPark_Comprehensive.postman_collection.json`
4. Click **Import**

### Step 2: Configure Environment Variables
In Postman, set these collection variables:
- **token**: Leave empty initially (filled by login response)
- **vehicleId**: `1` (default vehicle ID)
- **lotId**: `1` (default lot ID)
- **sessionId**: `1` (default session ID)

### Step 3: Start Application
Set environment variables and start the Spring Boot application:

**PowerShell (Windows):**
```powershell
$env:SMARTPARK_JWT_SECRET = "i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg="
$env:SMARTPARK_USERNAME = "admin"
$env:SMARTPARK_PASSWORD = "SmartPark123!"
cd C:\Git\SmartPark-Spring-Boot\smartpark-spring-boot
# Use IntelliJ IDE or Maven to run: mvn spring-boot:run
```

Application will start on `http://localhost:8080`

## Test Folders & Endpoints

### 1. Authentication Tests (3 endpoints)
Tests JWT login functionality and credential validation.

| Endpoint | Method | Description | Expected Status |
|----------|--------|-------------|-----------------|
| Login - Valid Credentials | POST /api/login | Test with correct username/password | 200 OK |
| Login - Invalid Username | POST /api/login | Test with non-existent user | 401 Unauthorized |
| Login - Invalid Password | POST /api/login | Test with wrong password | 401 Unauthorized |

**Sample Request:**
```json
{
  "username": "admin",
  "password": "SmartPark123!"
}
```

**Sample Response (Success):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Login successful"
}
```

### 2. Vehicle Tests (8 endpoints)
Tests vehicle registration, retrieval, listing, and error handling.

| Endpoint | Method | Description | Expected Status |
|----------|--------|-------------|-----------------|
| Register Vehicle - Car | POST /api/vehicles | Register new CAR (ABC-1234) | 201 Created |
| Register Vehicle - Motorcycle | POST /api/vehicles | Register new MOTORCYCLE (MOTO-001) | 201 Created |
| Register Vehicle - Truck | POST /api/vehicles | Register new TRUCK (TRK-2025) | 201 Created |
| Get All Vehicles | GET /api/vehicles | List all registered vehicles | 200 OK |
| Get Vehicle by ID | GET /api/vehicles/{id} | Retrieve specific vehicle | 200 OK |
| Register Vehicle - Duplicate | POST /api/vehicles | Try registering duplicate plate | 409 Conflict |
| Register Vehicle - Invalid Type | POST /api/vehicles | Use invalid vehicle type | 400 Bad Request |
| Register Vehicle - Missing Token | POST /api/vehicles | Request without authorization | 403 Forbidden |

**Sample Request:**
```json
{
  "plateNumber": "ABC-1234",
  "vehicleType": "CAR"
}
```

**Valid Vehicle Types:** `CAR`, `MOTORCYCLE`, `TRUCK`

### 3. Parking Lot Tests (7 endpoints)
Tests parking lot management, slot availability, and occupancy tracking.

| Endpoint | Method | Description | Expected Status |
|----------|--------|-------------|-----------------|
| Create Parking Lot - Building A | POST /api/lots | Create lot with 50 slots | 201 Created |
| Create Parking Lot - Building B | POST /api/lots | Create another lot with 30 slots | 201 Created |
| Get All Parking Lots | GET /api/lots | List all parking lots | 200 OK |
| Get Parking Lot by ID | GET /api/lots/{id} | Get specific lot details | 200 OK |
| Get Lot Occupancy | GET /api/lots/{id}/occupancy | Check current occupancy | 200 OK |
| Create Parking Lot - Duplicate Name | POST /api/lots | Try duplicate lot name | 409 Conflict |
| Create Parking Lot - Invalid Slots | POST /api/lots | Create with availableSlots > totalSlots | 400 Bad Request |

**Sample Request:**
```json
{
  "name": "Building A",
  "totalSlots": 50,
  "availableSlots": 50
}
```

**Occupancy Response:**
```json
{
  "lotId": 1,
  "name": "Building A",
  "totalSlots": 50,
  "occupiedSlots": 2,
  "availableSlots": 48,
  "occupancyPercentage": 4.0
}
```

### 4. Parking Session Tests (10 endpoints)
Tests check-in/check-out operations and fee calculations for all vehicle types.

| Endpoint | Method | Description | Expected Status |
|----------|--------|-------------|-----------------|
| Check-In Car | POST /api/lots/{id}/check-in | Park a CAR | 201 Created |
| Check-In Motorcycle | POST /api/lots/{id}/check-in | Park a MOTORCYCLE | 201 Created |
| Check-In Truck | POST /api/lots/{id}/check-in | Park a TRUCK | 201 Created |
| Check-Out Car | POST /api/lots/{id}/check-out | Exit with CAR | 200 OK |
| Check-Out Motorcycle | POST /api/lots/{id}/check-out | Exit with MOTORCYCLE | 200 OK |
| Check-Out Truck | POST /api/lots/{id}/check-out | Exit with TRUCK | 200 OK |
| Get All Sessions | GET /api/sessions | List all parking sessions | 200 OK |
| Get Session by ID | GET /api/sessions/{id} | Get session details & fees | 200 OK |
| Check-In - No Available Slots | POST /api/lots/{id}/check-in | Exceed lot capacity | 400 Bad Request |
| Check-Out - Invalid Session | POST /api/lots/{id}/check-out | Attempt with non-existent ID | 404 Not Found |

**Check-In Request:**
```json
{
  "vehicleId": 1,
  "lotId": 1
}
```

**Check-Out Request:**
```json
{
  "sessionId": 1
}
```

**Fee Calculation (Pricing Model):**
- **CAR**: 60 PHP base + 20 PHP/hour (0-3 hours included)
- **MOTORCYCLE**: 30 PHP base + 10 PHP/hour (0-3 hours included)
- **TRUCK**: 120 PHP base + 40 PHP/hour (0-3 hours included)

### 5. Error & Edge Cases (5 endpoints)
Tests authentication failures, malformed requests, and boundary conditions.

| Endpoint | Method | Description | Expected Status |
|----------|--------|-------------|-----------------|
| Get Vehicles - No Token | GET /api/vehicles | Request without JWT token | 403 Forbidden |
| Get Lots - No Token | GET /api/lots | Request without JWT token | 403 Forbidden |
| Register - Empty Plate | POST /api/vehicles | Submit blank plate number | 400 Bad Request |
| Create Lot - Negative Slots | POST /api/lots | Use negative slot numbers | 400 Bad Request |
| Get Non-Existent Vehicle | GET /api/vehicles/9999 | Request ID that doesn't exist | 404 Not Found |

## Test Execution Flow

### Recommended Order:
1. **Run Authentication Tests first** to obtain a valid JWT token
   - Copy the token from the response and set it as the `{{token}}` variable
2. **Run Vehicle Tests** to register test vehicles
3. **Run Parking Lot Tests** to create parking lots
4. **Run Parking Session Tests** to test check-in/check-out flows
5. **Run Error & Edge Cases** to verify error handling

### Automatic Token Capture
Each login request in the collection includes a test script that automatically captures the JWT token:

```javascript
pm.environment.set("token", pm.response.json().token);
```

After running "Login - Valid Credentials", the token is automatically available for subsequent requests.

## Sample Test Scenarios

### Scenario 1: Complete Parking Session
1. Login → Get token
2. Register 3 vehicles (CAR, MOTORCYCLE, TRUCK)
3. Create 2 parking lots
4. Check-in all 3 vehicles
5. Get occupancy for both lots
6. Check-out all vehicles
7. Verify fees are calculated correctly

### Scenario 2: Error Handling
1. Try login with invalid credentials → Expect 401
2. Try register vehicle without token → Expect 403
3. Try duplicate vehicle registration → Expect 409
4. Try check-in to full lot → Expect 400
5. Try check-out non-existent session → Expect 404

### Scenario 3: Concurrent Operations
1. Check-in multiple vehicles to same lot (tests pessimistic locking)
2. Verify occupancy updates correctly
3. Ensure no race conditions in slot availability

## Preloaded Sample Data

When application starts, these vehicles and lots are automatically created:

### Vehicles:
- **ABC-1234** (CAR)
- **MOTO-001** (MOTORCYCLE)
- **TRK-2025** (TRUCK)

### Parking Lots:
- **Building A** (50 slots)
- **Building B** (30 slots)

### Login Credentials:
- **Username:** admin
- **Password:** SmartPark123!

## Tips & Troubleshooting

### Token Expired?
- Re-run the "Login - Valid Credentials" request
- The token is automatically captured and updated in `{{token}}`

### "No Available Slots" Error?
- Check lot occupancy with "Get Lot Occupancy"
- Create additional parking lots or check out existing vehicles

### CORS Issues?
- Collection is configured for `http://localhost:8080`
- Ensure application is running on default port

### Validation Errors?
- Verify vehicle types are exactly: `CAR`, `MOTORCYCLE`, `TRUCK` (case-sensitive)
- Ensure plate numbers are unique across registration attempts
- Check that available slots ≤ total slots for parking lots

## Next Steps

1. **Extend Collection:** Add more test scenarios based on business requirements
2. **Automate Tests:** Use Postman's collection runner for CI/CD integration
3. **Performance Testing:** Use Newman CLI with load testing tools
4. **Environment-Specific:** Create separate Postman environments for dev/staging/prod

---

