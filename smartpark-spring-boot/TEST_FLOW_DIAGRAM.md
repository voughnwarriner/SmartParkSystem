# SmartPark Testing Flow Diagram

## Complete Test Execution Flow

```
┌──────────────────────────────────────────────────────────────────────────────┐
│                   START: IMPORT POSTMAN COLLECTION                          │
│         SmartPark_Comprehensive.postman_collection.json (33 endpoints)      │
└──────────────────────────────────────────────────────────────────────────────┘
                                       │
                                       ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                    FOLDER 1: AUTHENTICATION TESTS                            │
│                              (3 endpoints)                                   │
├──────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 1️⃣  POST /api/login - Valid Credentials (admin / SmartPark123!)    │   │
│  │     ✓ Expected: 200 OK + JWT token                                  │   │
│  │     📌 Auto-capture token → {{token}} variable                      │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 2️⃣  POST /api/login - Invalid Username                             │   │
│  │     ✓ Expected: 401 Unauthorized                                    │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 3️⃣  POST /api/login - Invalid Password                             │   │
│  │     ✓ Expected: 401 Unauthorized                                    │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└──────────────────────────────────────────────────────────────────────────────┘
                                       │
                                       ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                      FOLDER 2: VEHICLE TESTS                                 │
│                         (8 endpoints)                                        │
├──────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 1️⃣  POST /api/vehicles - Register CAR (ABC-1234)                    │   │
│  │     Header: Authorization: Bearer {{token}}                         │   │
│  │     ✓ Expected: 201 Created                                         │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 2️⃣  POST /api/vehicles - Register MOTORCYCLE (MOTO-001)             │   │
│  │     ✓ Expected: 201 Created                                         │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 3️⃣  POST /api/vehicles - Register TRUCK (TRK-2025)                  │   │
│  │     ✓ Expected: 201 Created                                         │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 4️⃣  GET /api/vehicles - List All Vehicles ✨ NEW ENDPOINT          │   │
│  │     ✓ Expected: 200 OK + Array of 3 vehicles                        │   │
│  │     📌 Verify preloaded sample data                                 │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 5️⃣  GET /api/vehicles/{id} - Get Vehicle by ID                      │   │
│  │     ✓ Expected: 200 OK + Vehicle details                            │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 6️⃣  POST /api/vehicles - Duplicate Plate (ERROR TEST)               │   │
│  │     Body: {"plateNumber":"ABC-1234","vehicleType":"CAR"}            │   │
│  │     ✓ Expected: 409 Conflict                                        │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 7️⃣  POST /api/vehicles - Invalid Type (ERROR TEST)                  │   │
│  │     Body: {"plateNumber":"INVALID-1","vehicleType":"INVALID_TYPE"} │   │
│  │     ✓ Expected: 400 Bad Request                                     │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 8️⃣  POST /api/vehicles - Missing Token (ERROR TEST)                 │   │
│  │     ✗ No Authorization header                                       │   │
│  │     ✓ Expected: 403 Forbidden                                       │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└──────────────────────────────────────────────────────────────────────────────┘
                                       │
                                       ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                    FOLDER 3: PARKING LOT TESTS                               │
│                         (7 endpoints)                                        │
├──────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 1️⃣  POST /api/lots - Create Lot "Building A" (50 slots)             │   │
│  │     ✓ Expected: 201 Created                                         │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 2️⃣  POST /api/lots - Create Lot "Building B" (30 slots)             │   │
│  │     ✓ Expected: 201 Created                                         │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 3️⃣  GET /api/lots - List All Parking Lots                           │   │
│  │     ✓ Expected: 200 OK + Array of 2 lots                            │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 4️⃣  GET /api/lots/{id} - Get Lot by ID                              │   │
│  │     ✓ Expected: 200 OK + Lot details                                │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 5️⃣  GET /api/lots/{id}/occupancy - Get Occupancy Stats              │   │
│  │     ✓ Expected: 200 OK + {"occupiedSlots":0,"availableSlots":50}    │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 6️⃣  POST /api/lots - Duplicate Name (ERROR TEST)                    │   │
│  │     ✓ Expected: 409 Conflict                                        │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 7️⃣  POST /api/lots - Invalid Slots (ERROR TEST)                     │   │
│  │     Body: availableSlots (20) > totalSlots (10)                     │   │
│  │     ✓ Expected: 400 Bad Request                                     │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└──────────────────────────────────────────────────────────────────────────────┘
                                       │
                                       ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                  FOLDER 4: PARKING SESSION TESTS                             │
│                         (10 endpoints)                                       │
├──────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 1️⃣  POST /api/lots/{id}/check-in - Car (ABC-1234) in Building A     │   │
│  │     Body: {"vehicleId":1,"lotId":1}                                 │   │
│  │     ✓ Expected: 201 Created                                         │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 2️⃣  POST /api/lots/{id}/check-in - Motorcycle (MOTO-001)            │   │
│  │     ✓ Expected: 201 Created                                         │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 3️⃣  POST /api/lots/{id}/check-in - Truck (TRK-2025) in Building B   │   │
│  │     ✓ Expected: 201 Created                                         │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 4️⃣  POST /api/lots/{id}/check-out - Car (SessionId: 1)              │   │
│  │     💰 Fee Calculation: CAR base 60 PHP                             │   │
│  │     ✓ Expected: 200 OK + {"sessionId":1,"amountDue":60,"..."}      │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 5️⃣  POST /api/lots/{id}/check-out - Motorcycle (SessionId: 2)       │   │
│  │     💰 Fee Calculation: MOTORCYCLE base 30 PHP                      │   │
│  │     ✓ Expected: 200 OK + {"amountDue":30,"..."}                    │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 6️⃣  POST /api/lots/{id}/check-out - Truck (SessionId: 3)            │   │
│  │     💰 Fee Calculation: TRUCK base 120 PHP                          │   │
│  │     ✓ Expected: 200 OK + {"amountDue":120,"..."}                   │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 7️⃣  GET /api/sessions - List All Parking Sessions                   │   │
│  │     ✓ Expected: 200 OK + Array of 3 completed sessions             │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 8️⃣  GET /api/sessions/{id} - Get Session Details (SessionId: 1)     │   │
│  │     ✓ Expected: 200 OK + Session with fee details                   │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 9️⃣  POST /api/lots/{id}/check-in - No Slots (ERROR TEST)            │   │
│  │     Try to check-in when lot is full                                │   │
│  │     ✓ Expected: 400 Bad Request                                     │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 🔟  POST /api/lots/{id}/check-out - Invalid Session (ERROR TEST)    │   │
│  │     SessionId: 999 (doesn't exist)                                  │   │
│  │     ✓ Expected: 404 Not Found                                       │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└──────────────────────────────────────────────────────────────────────────────┘
                                       │
                                       ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                 FOLDER 5: ERROR & EDGE CASES                                 │
│                         (5 endpoints)                                        │
├──────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 1️⃣  GET /api/vehicles - No Token                                    │   │
│  │     ✗ Missing Authorization header                                  │   │
│  │     ✓ Expected: 403 Forbidden                                       │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 2️⃣  GET /api/lots - No Token                                        │   │
│  │     ✗ Missing Authorization header                                  │   │
│  │     ✓ Expected: 403 Forbidden                                       │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 3️⃣  POST /api/vehicles - Empty Plate Number                         │   │
│  │     Body: {"plateNumber":"","vehicleType":"CAR"}                    │   │
│  │     ✓ Expected: 400 Bad Request                                     │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 4️⃣  POST /api/lots - Negative Slots                                 │   │
│  │     Body: {"name":"...","totalSlots":-10,"availableSlots":0}       │   │
│  │     ✓ Expected: 400 Bad Request                                     │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                       │                                      │
│                                       ▼                                      │
│  ┌─────────────────────────────────────────────────────────────────────┐   │
│  │ 5️⃣  GET /api/vehicles/{id} - Non-Existent ID (9999)                 │   │
│  │     ✓ Expected: 404 Not Found                                       │   │
│  └─────────────────────────────────────────────────────────────────────┘   │
│                                                                              │
└──────────────────────────────────────────────────────────────────────────────┘
                                       │
                                       ▼
┌──────────────────────────────────────────────────────────────────────────────┐
│                        🎉 ALL TESTS COMPLETE 🎉                             │
│                  Verify all 33 endpoints working correctly                  │
└──────────────────────────────────────────────────────────────────────────────┘
```

---

## Fee Calculation Examples

### Example 1: CAR Parking for 2 Hours
```
Vehicle Type: CAR
Entry Time:   10:00 AM
Exit Time:    12:00 PM
Duration:     2 hours (within 0-3 hour base period)
Fee:          60 PHP (base fee only)
```

### Example 2: MOTORCYCLE Parking for 4 Hours
```
Vehicle Type: MOTORCYCLE
Entry Time:   10:00 AM
Exit Time:    2:00 PM
Duration:     4 hours (exceeds 3-hour base period by 1 hour)
Fee Calculation:
  - Base Fee:        30 PHP
  - Extra Hours:     1 hour @ 10 PHP/hour = 10 PHP
  - Total:           40 PHP
```

### Example 3: TRUCK Parking for 6.5 Hours
```
Vehicle Type: TRUCK
Entry Time:   10:00 AM
Exit Time:    4:30 PM
Duration:     6.5 hours (exceeds 3-hour base period by 3.5 hours)
Fee Calculation:
  - Base Fee:        120 PHP
  - Extra Hours:     ceil(3.5) = 4 hours @ 40 PHP/hour = 160 PHP
  - Total:           280 PHP
```

---

## Data Flow Diagram

```
┌─────────────────────────┐
│    POSTMAN REQUEST      │
│  (33 Test Endpoints)    │
└────────────┬────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│   Spring Boot Application (Port 8080)   │
├─────────────────────────────────────────┤
│                                         │
│  SecurityConfig                         │
│  ├─ JWT Token Validation                │
│  └─ Authorization Filter                │
│                                         │
│  Controllers                            │
│  ├─ AuthenticationController (/login)   │
│  ├─ VehicleController (/vehicles) ✨    │
│  ├─ ParkingLotController (/lots)        │
│  └─ ParkingSessionController (/sessions)│
│                                         │
│  Services                               │
│  ├─ ParkingService (business logic)     │
│  ├─ JwtService (token management)       │
│  └─ AutoRemovalService (scheduled)      │
│                                         │
│  Repositories                           │
│  ├─ VehicleRepository                   │
│  ├─ ParkingLotRepository                │
│  └─ ParkingSessionRepository            │
│                                         │
│  Exception Handlers                     │
│  └─ Global error responses              │
│                                         │
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────────────────────┐
│   H2 Database (smartparkdb)             │
├─────────────────────────────────────────┤
│                                         │
│  Tables:                                │
│  ├─ vehicles (3 preloaded)              │
│  ├─ parking_lots (2 preloaded)          │
│  └─ parking_sessions (auto-managed)     │
│                                         │
└────────────┬────────────────────────────┘
             │
             ▼
┌─────────────────────────┐
│   POSTMAN RESPONSE      │
│  (200, 201, 400, 404)   │
└─────────────────────────┘
```

---

## Key Testing Concepts

### Token Management
- **Initial Request:** POST /api/login → Receive JWT token
- **Subsequent Requests:** Include token in Authorization header: `Bearer {{token}}`
- **Auto-Capture:** Test script automatically updates `{{token}}` variable

### Status Codes Expected
- **201 Created:** POST operations (register, create, check-in)
- **200 OK:** GET operations and check-out (with fee calculated)
- **400 Bad Request:** Validation errors (invalid data)
- **401 Unauthorized:** Invalid login credentials
- **403 Forbidden:** Missing/invalid JWT token
- **404 Not Found:** Resource not found
- **409 Conflict:** Duplicate resources

### Pessimistic Locking
- When vehicle checks in, database locks the vehicle and lot records
- Prevents race conditions if two requests try to park same vehicle simultaneously
- Locks released after check-in completes

### Pricing Algorithm
- Base fee covers first 0-3 hours of parking
- Additional hours charged separately: `ceil(duration_hours - 3) * hourly_rate`
- Different rates for CAR, MOTORCYCLE, TRUCK

---

## Success Metrics

✅ **All tests should pass if:**
- All 33 endpoints return expected status codes
- JWT token is correctly captured and used
- All 3 vehicles are registered
- All 2 parking lots created
- Fee calculations are accurate
- Error scenarios return appropriate error codes
- Occupancy tracking is accurate

---
