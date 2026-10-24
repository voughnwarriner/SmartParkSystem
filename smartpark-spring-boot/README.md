# SmartPark — Spring Boot REST API

A Java/Spring Boot implementation of the SmartPark Java assessment. It uses an H2 in-memory database, JWT bearer authentication, JPA, request validation, and a scheduled job that automatically ends parking sessions older than 15 minutes.

## Requirements

- Java 17 or newer
- Maven 3.8+ (or use the Maven Wrapper if you add one)
- No external database server is required

> This project uses Spring Boot 3.4.5, which requires Java 17+. The assessment permits Java 8 or higher; Java 17 satisfies that requirement.

## Important demo credentials and security

Default local assessment login:

- Username: `admin`
- Password: `SmartPark123!`

The credentials are configurable through `SMARTPARK_USERNAME` and `SMARTPARK_PASSWORD`.

**Set a real JWT signing secret before starting the application.** The property `SMARTPARK_JWT_SECRET` must be a Base64-encoded secret decoding to at least 32 bytes. Generate one with:

```bash
openssl rand -base64 32
```

macOS/Linux example:

```bash
export SMARTPARK_JWT_SECRET="$(openssl rand -base64 32)"
export SMARTPARK_USERNAME="admin"
export SMARTPARK_PASSWORD="SmartPark123!"
mvn spring-boot:run
```

Windows PowerShell example:

```powershell
$env:SMARTPARK_JWT_SECRET = [Convert]::ToBase64String((1..32 | ForEach-Object { Get-Random -Maximum 256 }))
$env:SMARTPARK_USERNAME = "admin"
$env:SMARTPARK_PASSWORD = "SmartPark123!"
mvn spring-boot:run
```

**Windows PowerShell (with example secret for testing):**

If you want to use a pre-generated Base64 secret for quick testing, run:

```powershell
$env:SMARTPARK_JWT_SECRET = "i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg="
$env:SMARTPARK_USERNAME = "admin"
$env:SMARTPARK_PASSWORD = "SmartPark123!"
mvn spring-boot:run
```

> **Note:** The example secret above is for development/testing only. Generate a fresh secret for each new environment using the command above.

Do not commit secrets to Git or use the assessment credentials in a real deployment.

## Build, test, and run

```bash
mvn clean test
mvn package
mvn spring-boot:run
```

The API runs at `http://localhost:8080`.

### Option 1: Run from Command Line (PowerShell / Terminal)

**Windows PowerShell:**

```powershell
# Set environment variables for this session
$env:SMARTPARK_JWT_SECRET = "i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg="
$env:SMARTPARK_USERNAME = "admin"
$env:SMARTPARK_PASSWORD = "SmartPark123!"

# Run the application
mvn spring-boot:run
```

**macOS / Linux (Bash):**

```bash
# Set environment variables for this session
export SMARTPARK_JWT_SECRET="i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg="
export SMARTPARK_USERNAME="admin"
export SMARTPARK_PASSWORD="SmartPark123!"

# Run the application
mvn spring-boot:run
```

### Option 2: Run Configuration in IntelliJ IDE (Recommended)

This approach keeps configuration persistent and makes development easier.

#### Steps:

1. **Open Run Configuration Dialog:**
   - Click `Run` menu → `Edit Configurations...` (or press `Alt+Shift+F10`)

2. **Create New Maven Run Configuration:**
   - Click the `+` button to add a new configuration
   - Select `Maven` from the dropdown

3. **Configure the Following:**

   | Field | Value |
   |-------|-------|
   | **Name** | `SmartPark Spring Boot` |
   | **Working directory** | `$PROJECT_DIR$` (auto-filled) |
   | **Command line** | `spring-boot:run` |

4. **Add Environment Variables:**
   - Click on `Environment variables` field
   - Click the `...` button to open the environment variables editor
   - Add these three variables:

   | Name | Value |
   |------|-------|
   | `SMARTPARK_JWT_SECRET` | `i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg=` |
   | `SMARTPARK_USERNAME` | `admin` |
   | `SMARTPARK_PASSWORD` | `SmartPark123!` |

   **Screenshot guide:**
   - Name field: Type the variable name
   - Value field: Type the value
   - Click `+` to add each variable
   - Click `OK` when done

5. **Save the Configuration:**
   - Click `OK` to save and close the dialog

6. **Run the Application:**
   - Select `SmartPark Spring Boot` from the run configuration dropdown (top-right)
   - Click the green play button (▶) or press `Shift+F10`

7. **Verify the Application Started:**
   - Look for: `Tomcat initialized with port 8080 (http)`
   - Then: `Root WebApplicationContext: initialization completed`
   - Finally: Application is ready at `http://localhost:8080`

#### Example Configuration (Step-by-Step Visual):

```
┌─────────────────────────────────────────────────────────────────┐
│ Run/Debug Configurations                                        │
├─────────────────────────────────────────────────────────────────┤
│ Name: SmartPark Spring Boot                                     │
│ Working directory: /path/to/smartpark-spring-boot               │
│ Command line: spring-boot:run                                   │
│                                                                  │
│ Environment variables:                                          │
│ ✓ SMARTPARK_JWT_SECRET=i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+... │
│ ✓ SMARTPARK_USERNAME=admin                                      │
│ ✓ SMARTPARK_PASSWORD=SmartPark123!                              │
│                                                                  │
│ [Cancel]                                           [OK]         │
└─────────────────────────────────────────────────────────────────┘
```

### Option 3: System Environment Variables (Persistent)

If you want the environment variables available to **all applications** on your system:

**Windows (GUI):**

1. Press `Win + X` → `System` → `Advanced system settings`
2. Click `Environment Variables` button
3. Under "User variables" or "System variables", click `New`
4. Add these three variables:
   - `SMARTPARK_JWT_SECRET` = `i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg=`
   - `SMARTPARK_USERNAME` = `admin`
   - `SMARTPARK_PASSWORD` = `SmartPark123!`
5. Click `OK` and restart IntelliJ/terminal for changes to take effect

**Windows (PowerShell):**

```powershell
# Set permanently for current user
[Environment]::SetEnvironmentVariable("SMARTPARK_JWT_SECRET", "i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg=", "User")
[Environment]::SetEnvironmentVariable("SMARTPARK_USERNAME", "admin", "User")
[Environment]::SetEnvironmentVariable("SMARTPARK_PASSWORD", "SmartPark123!", "User")

# Restart your IDE/terminal for changes to take effect
```

**macOS / Linux:**

```bash
# Add to ~/.zshrc or ~/.bashrc
export SMARTPARK_JWT_SECRET="i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg="
export SMARTPARK_USERNAME="admin"
export SMARTPARK_PASSWORD="SmartPark123!"

# Reload shell config
source ~/.zshrc  # or ~/.bashrc
```

### Quickest Way to Start (Recommended for Development)

Use **Option 2** (IntelliJ Run Configuration) for the best developer experience:

1. Configure once ✅
2. Always available in dropdown ✅
3. No terminal commands needed ✅
4. Easy to switch between configurations ✅
5. Environment variables isolated to this config ✅

H2 is configured as an in-memory database. Data is recreated on application restart. Sample lots and vehicles are preloaded on startup.

H2 console (local development only): `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:smartparkdb`
- User: `sa`
- Password: (blank)

## Authentication

All `/api/**` endpoints except `POST /api/auth/login` require:

```http
Authorization: Bearer <accessToken>
```

Tokens are signed with HS256 and expire after 15 minutes by default.

### 1. Log in

```bash
curl -i -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"SmartPark123!"}'
```

Copy `accessToken` from the JSON response.

For the examples below, set a shell variable:

```bash
TOKEN="<paste-accessToken-here>"
```

PowerShell users can assign `$TOKEN = "<paste-accessToken-here>"`.

### 2. Create a parking lot

```bash
curl -i -X POST http://localhost:8080/api/lots \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"lotId":"LOT-003","location":"Quezon City","capacity":15}'
```

### 3. Register a vehicle

```bash
curl -i -X POST http://localhost:8080/api/vehicles \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"licensePlate":"NEW-123","type":"CAR","ownerName":"Alex Santos"}'
```

Allowed vehicle types: `MOTORCYCLE`, `CAR`, `TRUCK`. License plates accept only letters, numbers, and dashes. Owner names accept letters and spaces.

Sample preloaded vehicles:

- `ABC-1234` — CAR
- `MOTO-001` — MOTORCYCLE
- `TRK-2025` — TRUCK

Sample preloaded lots:

- `LOT-001` — Makati CBD, capacity 20
- `LOT-002` — BGC, Taguig, capacity 10

### 4. Check in a vehicle

```bash
curl -i -X POST http://localhost:8080/api/lots/LOT-001/check-in \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"licensePlate":"ABC-1234"}'
```

### 5. Check out and get the fee

```bash
curl -i -X POST http://localhost:8080/api/lots/LOT-001/check-out \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"licensePlate":"ABC-1234"}'
```

### 6. Check occupancy

```bash
curl -i http://localhost:8080/api/lots/LOT-001/occupancy \
  -H "Authorization: Bearer $TOKEN"
```

### 7. List vehicles currently parked in a lot

```bash
curl -i http://localhost:8080/api/lots/LOT-001/vehicles \
  -H "Authorization: Bearer $TOKEN"
```

### 7. List vehicles currently parked in a lot

```bash
curl -i http://localhost:8080/api/lots/LOT-001/vehicles \
  -H "Authorization: ******"
```

### 8. List all registered vehicles

```bash
curl -i http://localhost:8080/api/vehicles \
  -H "Authorization: ******"
```

Response example:
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

### 9. Other endpoints

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/auth/login` | Obtain JWT |
| POST | `/api/lots` | Register lot |
| GET | `/api/lots` | List lots |
| GET | `/api/lots/{lotId}` | Lot details |
| POST | `/api/vehicles` | Register vehicle |
| GET | `/api/vehicles` | **List all registered vehicles** ✨ |
| GET | `/api/vehicles/{licensePlate}` | Vehicle details |
| POST | `/api/lots/{lotId}/check-in` | Check in |
| POST | `/api/lots/{lotId}/check-out` | Check out and calculate fee |
| GET | `/api/lots/{lotId}/occupancy` | Occupancy and available spaces |
| GET | `/api/lots/{lotId}/vehicles` | Currently parked vehicles |

## Pricing rules

| Vehicle type | Base fee (up to first 3 hours) | Each started hour beyond 3 hours |
|---|---:|---:|
| MOTORCYCLE | PHP 30.00 | PHP 10.00 |
| CAR | PHP 60.00 | PHP 20.00 |
| TRUCK | PHP 120.00 | PHP 40.00 |

Any partial hour is rounded up for billing. For example, 3 hours and 1 second is billed as 4 hours. A stay of up to 3 hours uses the base fee.

The assessment also requires automatic removal after **more than 15 minutes**. A scheduled job checks every 30 seconds and marks overdue sessions as `AUTO_REMOVED`, records the end time and calculated fee, and releases the lot space. This is a simulated system status change; it does not physically retrieve a vehicle. The 15-minute automatic removal requirement means a normal live session will ordinarily end long before the three-hour base period expires.

## Status and errors

- `401 Unauthorized`: missing, invalid, or expired JWT, or invalid login credentials.
- `404 Not Found`: unknown lot/vehicle or no active session to check out.
- `409 Conflict`: duplicate IDs, full lot, or vehicle already parked.
- `400 Bad Request`: request validation errors or malformed JSON.

Error responses are JSON.

## Tests

`ParkingFeeCalculatorTest` covers base pricing, additional-hour pricing, partial-hour rounding, and invalid timestamps. Run all tests with `mvn test`.

## Design notes / assumptions

- The login uses static configurable credentials as allowed by the assessment.
- Parking lots, vehicles, and parking sessions are stored in H2 using JPA.
- Active parking sessions determine which vehicles are currently parked. Occupancy is updated in the same transaction as check-in, checkout, and automatic removal.
- A vehicle can have at most one active session, enforced by a pessimistic lock on its row during check-in.
- The lot row is pessimistically locked during capacity-changing operations to protect the last available space from concurrent check-ins.
- The fee is determined by vehicle type, not by the parking lot. The original per-minute lot fee is therefore not used.
- H2 is in-memory, so all data resets when the application process stops.
- The H2 console is enabled for local assessment use only; disable it for production.
