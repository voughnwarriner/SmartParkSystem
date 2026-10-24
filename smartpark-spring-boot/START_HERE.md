# 🚀 SmartPark Project - START HERE

Welcome to SmartPark! This guide will help you get started quickly.

## ⚡ Quick Start (5 minutes)

### Step 1: Set Environment Variables (PowerShell)
```powershell
$env:SMARTPARK_JWT_SECRET = "i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg="
$env:SMARTPARK_USERNAME = "admin"
$env:SMARTPARK_PASSWORD = "SmartPark123!"
```

### Step 2: Start the Application
```powershell
cd C:\Git\SmartPark-Spring-Boot\smartpark-spring-boot
mvn spring-boot:run
# OR use IntelliJ IDE Run button
```

### Step 3: Test with Postman
1. Open Postman
2. Import `postman/SmartPark_Comprehensive.postman_collection.json`
3. Run "Authentication Tests" → Login
4. Run remaining test folders

**✓ Application is ready at http://localhost:8080**

---

## 📚 Documentation Guide

### For First-Time Setup
**→ Read:** [`ENV_SETUP_QUICK_REFERENCE.md`](ENV_SETUP_QUICK_REFERENCE.md)
- JWT secret explanation
- 3 ways to set environment variables
- Verification checklist
- Common troubleshooting

### For Project Overview
**→ Read:** [`PROJECT_COMPLETION_SUMMARY.md`](PROJECT_COMPLETION_SUMMARY.md)
- Complete project status
- Architecture overview
- All implemented features
- Test coverage summary

### For API Documentation
**→ Read:** [`README.md`](README.md)
- Project description
- Build instructions
- All 11 API endpoints
- Database schema
- Pricing model

### For Testing & Postman
**→ Read:** [`postman/COMPREHENSIVE_COLLECTION_GUIDE.md`](postman/COMPREHENSIVE_COLLECTION_GUIDE.md)
- 33 test endpoints organized in 5 folders
- Test execution flow
- Sample requests/responses
- Troubleshooting tips

### For Detailed Test Scenarios
**→ Read:** [`postman/TEST_SCENARIOS.md`](postman/TEST_SCENARIOS.md)
- 30+ test scenarios
- Expected request/response examples
- Edge cases and error conditions
- Pricing calculation examples

---

## 🎯 What's Included

### ✨ New Features
- **GET /api/vehicles** - List all registered vehicles
  - Implementation: `VehicleController.getAll()` + `ParkingService.getAllVehicles()`
  - Returns preloaded sample data (3 vehicles)

### 📮 Postman Collection
- **SmartPark_Comprehensive.postman_collection.json**
  - 33 test endpoints in 5 organized folders
  - Automatic JWT token capture
  - Collection variables for dynamic testing
  - Ready-to-run test scenarios

### 📖 Complete Documentation
- **README.md** - Project documentation (updated)
- **ENV_SETUP_QUICK_REFERENCE.md** - Quick setup guide
- **PROJECT_COMPLETION_SUMMARY.md** - Completion summary
- **COMPREHENSIVE_COLLECTION_GUIDE.md** - Postman guide
- **TEST_SCENARIOS.md** - Test case documentation

### 🔐 Security
- JWT authentication (HS256)
- Stateless authorization
- Pessimistic locking for race conditions
- Environment-based configuration

### 💰 Pricing Model
| Vehicle | Base Fee (0-3 hrs) | Hourly Rate |
|---------|-------------------|-------------|
| CAR | 60 PHP | 20 PHP/hour |
| MOTORCYCLE | 30 PHP | 10 PHP/hour |
| TRUCK | 120 PHP | 40 PHP/hour |

---

## 🗺️ File Navigation

```
SmartPark Project
├── 📄 START_HERE.md ............................ (YOU ARE HERE)
├── 📄 README.md ............................... Main documentation
├── 📄 ENV_SETUP_QUICK_REFERENCE.md ............ Environment setup
├── 📄 PROJECT_COMPLETION_SUMMARY.md .......... Complete status
├── 📄 pom.xml ................................ Maven configuration
│
├── 📂 postman/ ............................... Postman collections
│   ├── SmartPark_Comprehensive.postman_collection.json (33 endpoints)
│   ├── SmartPark.postman_collection.json ... (original 11 endpoints)
│   ├── COMPREHENSIVE_COLLECTION_GUIDE.md ... Testing guide
│   ├── TEST_SCENARIOS.md ................... Test documentation
│   └── generate_collection.py ............. Script generator (Python)
│
├── 📂 src/main/java/com/smartpark/
│   ├── controller/
│   │   ├── VehicleController.java ✨ (contains new getAll() method)
│   │   ├── ParkingLotController.java
│   │   ├── ParkingSessionController.java
│   │   └── AuthenticationController.java
│   ├── service/
│   │   ├── ParkingService.java ✨ (contains new getAllVehicles() method)
│   │   ├── JwtService.java
│   │   └── AutoRemovalService.java
│   ├── repository/
│   │   ├── VehicleRepository.java
│   │   ├── ParkingLotRepository.java
│   │   └── ParkingSessionRepository.java
│   ├── entity/
│   │   ├── Vehicle.java
│   │   ├── ParkingLot.java
│   │   ├── ParkingSession.java
│   │   └── User.java
│   ├── dto/
│   │   ├── VehicleResponse.java
│   │   ├── ParkingLotResponse.java
│   │   └── ... (other DTOs)
│   ├── exception/
│   │   ├── ParkingLotNotFoundException.java
│   │   ├── VehicleAlreadyParked.java
│   │   └── ... (other exceptions)
│   ├── security/
│   │   ├── JwtAuthenticationFilter.java
│   │   └── JwtService.java
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   └── DataInitializer.java
│   └── SmartParkApplication.java
│
└── 📂 target/ (generated after build)
```

---

## 🔧 Environment Variables

**Must set all three before running:**

| Variable | Value | Purpose |
|----------|-------|---------|
| `SMARTPARK_JWT_SECRET` | `i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg=` | JWT signing key |
| `SMARTPARK_USERNAME` | `admin` | Default login username |
| `SMARTPARK_PASSWORD` | `SmartPark123!` | Default login password |

**Setup Methods:**
1. **PowerShell (temporary)** → See `ENV_SETUP_QUICK_REFERENCE.md` Option 1
2. **IntelliJ IDE** → See `ENV_SETUP_QUICK_REFERENCE.md` Option 2
3. **System Environment (permanent)** → See `ENV_SETUP_QUICK_REFERENCE.md` Option 3

---

## 🧪 Testing Checklist

- [ ] **Step 1:** Set environment variables
- [ ] **Step 2:** Start application (`mvn spring-boot:run`)
- [ ] **Step 3:** Verify startup: "Tomcat initialized with port 8080"
- [ ] **Step 4:** Import Postman collection
- [ ] **Step 5:** Run "Login - Valid Credentials"
- [ ] **Step 6:** Copy token to `{{token}}` variable
- [ ] **Step 7:** Run remaining test folders in order:
  - [ ] Authentication Tests
  - [ ] Vehicle Tests
  - [ ] Parking Lot Tests
  - [ ] Parking Session Tests
  - [ ] Error & Edge Cases
- [ ] **Step 8:** Verify all tests pass

---

## 📞 Quick Reference

### Login Credentials
```
Username: admin
Password: SmartPark123!
```

### API Base URL
```
http://localhost:8080
```

### Key Endpoints
| Method | Endpoint | Purpose |
|--------|----------|---------|
| POST | `/api/login` | Authenticate and get JWT token |
| POST | `/api/vehicles` | Register a vehicle |
| **GET** | **`/api/vehicles`** | **List all vehicles** ✨ NEW |
| GET | `/api/vehicles/{id}` | Get vehicle details |
| POST | `/api/lots` | Create parking lot |
| GET | `/api/lots` | List parking lots |
| POST | `/api/lots/{id}/check-in` | Vehicle check-in |
| POST | `/api/lots/{id}/check-out` | Vehicle check-out (calculate fee) |
| GET | `/api/sessions` | List all sessions |
| GET | `/api/sessions/{id}` | Get session details |
| GET | `/api/lots/{id}/occupancy` | Get lot occupancy stats |

---

## ❓ Common Questions

**Q: How do I change the JWT secret?**
A: Generate a new one with `openssl rand -base64 32` and set `SMARTPARK_JWT_SECRET` to the new value.

**Q: What if I get "Port 8080 already in use"?**
A: Either kill the process using port 8080 or change it in `application.properties: server.port=8081`

**Q: How do I get a valid JWT token?**
A: Call `POST /api/login` with `{"username":"admin","password":"SmartPark123!"}`. Postman automatically captures it.

**Q: What database is being used?**
A: H2 in-memory database (`jdbc:h2:mem:smartparkdb`). Data is lost on application restart.

**Q: How are parking fees calculated?**
A: Based on vehicle type + duration. See pricing table in README.md or TEST_SCENARIOS.md for examples.

---

## 📊 Project Stats

- **Total API Endpoints:** 11 (10 existing + 1 new)
- **Postman Test Endpoints:** 33 (comprehensive test coverage)
- **Test Scenarios:** 30+ documented scenarios
- **Documentation:** 5 detailed guides
- **Code Changes:** VehicleController + ParkingService (minimal, focused)
- **Security:** JWT HS256 + Pessimistic Locking
- **Database:** H2 In-Memory with 3 preloaded vehicles
- **Language:** Java 17
- **Framework:** Spring Boot 3.4.5

---

## 🎓 Learning Resources

### For Understanding Architecture
→ Read: `PROJECT_COMPLETION_SUMMARY.md` → "System Architecture" section

### For API Details
→ Read: `README.md` → "Endpoints" section

### For Testing
→ Read: `COMPREHENSIVE_COLLECTION_GUIDE.md` → "Test Execution Flow" section

### For Implementation
→ View source code:
- `src/main/java/com/smartpark/controller/VehicleController.java` (lines 31-33)
- `src/main/java/com/smartpark/service/ParkingService.java` (lines 76-78)

---

## ✅ Success Indicators

When the application starts successfully, you should see:

```
2026-10-24T16:11:23.810+08:00  INFO Starting SmartParkApplication using Java 17.0.12
2026-10-24T16:11:24.484+08:00  INFO Tomcat initialized with port 8080 (http)
2026-10-24T16:11:24.180+08:00  INFO Finished Spring Data repository scanning... Found 3 JPA repository interfaces
2026-10-24T16:11:24.520+08:00  INFO Root WebApplicationContext: initialization completed
```

**✓ Ready to accept API requests on http://localhost:8080**

---

## 🚀 Next Steps

1. **Immediate:** Follow the "Quick Start" section above
2. **Quick:** Run Postman tests using COMPREHENSIVE_COLLECTION_GUIDE.md
3. **Learning:** Review PROJECT_COMPLETION_SUMMARY.md for architecture
4. **Advanced:** Read source code and understand implementation patterns
5. **Production:** Consider containerization, logging, and database migration

---

