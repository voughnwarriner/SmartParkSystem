# SmartPark Environment Setup Quick Reference

## Required Environment Variables

| Variable | Value | Purpose |
|----------|-------|---------|
| `SMARTPARK_JWT_SECRET` | `i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg=` | JWT signing key (Base64, 32+ bytes) |
| `SMARTPARK_USERNAME` | `admin` | Default login username |
| `SMARTPARK_PASSWORD` | `SmartPark123!` | Default login password |

## Setup Methods

### Option 1: PowerShell (Current Session Only)
```powershell
# Set variables for current PowerShell session
$env:SMARTPARK_JWT_SECRET = "i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg="
$env:SMARTPARK_USERNAME = "admin"
$env:SMARTPARK_PASSWORD = "SmartPark123!"

# Verify
Get-ChildItem env:SMARTPARK* | ForEach-Object { Write-Host "$($_.Name) = $($_.Value)" }

# Start application
cd C:\Git\SmartPark-Spring-Boot\smartpark-spring-boot
mvn spring-boot:run
```

### Option 2: IntelliJ IDE Run Configuration
1. **Click** `Run` → `Edit Configurations...`
2. **Select** the SmartPark run configuration (or create new)
3. **Navigate** to `Environment variables`
4. **Add** these three variables:
   - `SMARTPARK_JWT_SECRET` = `i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg=`
   - `SMARTPARK_USERNAME` = `admin`
   - `SMARTPARK_PASSWORD` = `SmartPark123!`
5. **Click** `Apply` → `OK`
6. **Start** the application using Run → Run 'SmartParkApplication'

### Option 3: Permanent System Environment Variables (Windows)
1. **Press** `Win + X` → Select `System`
2. **Click** `Advanced system settings`
3. **Click** `Environment Variables...` button
4. **Under "User variables"**, click `New...`
5. **Add three entries:**
   - Variable name: `SMARTPARK_JWT_SECRET`, Value: `i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg=`
   - Variable name: `SMARTPARK_USERNAME`, Value: `admin`
   - Variable name: `SMARTPARK_PASSWORD`, Value: `SmartPark123!`
6. **Click** `OK` on all dialogs
7. **Restart** any open terminals or IDEs for changes to take effect

## JWT Secret Explanation

**What is it?**
- A Base64-encoded cryptographic key used to sign and verify JWT tokens
- Used by JwtService to secure authentication tokens
- Minimum 32 bytes (256 bits) required for HS256 algorithm

**Why is it required?**
- JwtService validates the secret during Spring Boot startup
- If missing or invalid, application fails with: `IllegalStateException: Set SMARTPARK_JWT_SECRET to a Base64-encoded secret of at least 32 bytes`
- This is intentional to catch configuration errors early

**How was it generated?**
```bash
# Generate a new 32-byte Base64 secret (if needed)
openssl rand -base64 32
```

**Current Secret Details:**
- **Value:** `i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg=`
- **Decoded bytes:** 32 bytes (256 bits)
- **Algorithm:** HS256 (HMAC with SHA-256)

## Verification Checklist

Before running the application, verify:

- [ ] `SMARTPARK_JWT_SECRET` is set to the exact Base64 value
- [ ] `SMARTPARK_USERNAME` is set to `admin`
- [ ] `SMARTPARK_PASSWORD` is set to `SmartPark123!`
- [ ] Java 17 or higher is installed (`java -version`)
- [ ] Maven is installed and in PATH (`mvn -version`)
- [ ] Port 8080 is available (not in use by another application)

## Testing Login

After starting the application, test the login endpoint:

**PowerShell:**
```powershell
$body = @{
    username = "admin"
    password = "SmartPark123!"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/login" `
  -Method POST `
  -ContentType "application/json" `
  -Body $body
```

**Expected Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "message": "Login successful"
}
```

## Common Issues & Solutions

### Issue: "Set SMARTPARK_JWT_SECRET to a Base64-encoded secret"
**Cause:** Environment variable not set or invalid value  
**Solution:** 
- Verify variable is set: `Write-Host $env:SMARTPARK_JWT_SECRET`
- Use exact value: `i3GUDQ3KBdCQZTDBv/5+MajapJ7T4vUvTS+lclwk3Dg=`
- For IDE: Restart IDE after setting system environment variables

### Issue: "Unable to start web server"
**Cause:** Could be JWT secret or other configuration issue  
**Solution:**
- Check console logs for "IllegalStateException"
- Verify all three environment variables are set
- Ensure they're set in the correct scope (session/IDE/system)

### Issue: Port 8080 already in use
**Cause:** Another application is using the port  
**Solution:**
- Change port in `application.properties`: `server.port=8081`
- Or kill the process using port 8080

## Postman Collection Setup

Once application is running:

1. **Import** `SmartPark_Comprehensive.postman_collection.json` into Postman
2. **Test** "Authentication Tests" folder first
3. **Copy token** from login response into `{{token}}` collection variable
4. **Run** remaining test folders in order

See `COMPREHENSIVE_COLLECTION_GUIDE.md` for detailed testing instructions.

---

**Keep this file handy!** It has everything needed to set up and run SmartPark locally.
