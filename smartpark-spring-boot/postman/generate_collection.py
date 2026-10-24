#!/usr/bin/env python3
"""
Generate a comprehensive Postman collection with multiple test scenarios for SmartPark API
"""

import json

def create_request(name, method, url, body=None, headers=None, test_script=None):
    """Helper to create a Postman request"""
    if headers is None:
        headers = []
    
    request = {
        "name": name,
        "request": {
            "method": method,
            "header": headers,
            "url": {
                "raw": url,
                "host": url.split("//")[1].split("/")[0].split(":")[0],
                "port": "8080" if "8080" in url else None,
                "path": url.split("/api/")[1].split("?")[0].split("/") if "/api/" in url else []
            }
        }
    }
    
    if body:
        request["request"]["body"] = {
            "mode": "raw",
            "raw": json.dumps(body, indent=2),
            "options": {"raw": {"language": "json"}}
        }
    
    if test_script:
        request["event"] = [{
            "listen": "test",
            "script": {
                "type": "text/javascript",
                "exec": test_script
            }
        }]
    
    return request

def generate_collection():
    """Generate the complete collection"""
    
    base_url = "{{baseUrl}}/api"
    
    # Authentication Tests
    auth_tests = [
        {
            "name": "1. Login - Valid Credentials",
            "request": {
                "method": "POST",
                "header": [{"key": "Content-Type", "value": "application/json"}],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"username": "admin", "password": "SmartPark123!"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/auth/login", "host": ["{{{{baseUrl}}}}"], "path": ["api", "auth", "login"]}
            },
            "event": [{
                "listen": "test",
                "script": {
                    "type": "text/javascript",
                    "exec": ["if (pm.response.code === 200) {", "  const body = pm.response.json();", "  pm.collectionVariables.set('token', body.accessToken);", "}"]
                }
            }]
        },
        {
            "name": "2. Login - Invalid Username",
            "request": {
                "method": "POST",
                "header": [{"key": "Content-Type", "value": "application/json"}],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"username": "wronguser", "password": "SmartPark123!"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/auth/login", "host": ["{{{{baseUrl}}}}"], "path": ["api", "auth", "login"]}
            }
        },
        {
            "name": "3. Login - Invalid Password",
            "request": {
                "method": "POST",
                "header": [{"key": "Content-Type", "value": "application/json"}],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"username": "admin", "password": "WrongPassword"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/auth/login", "host": ["{{{{baseUrl}}}}"], "path": ["api", "auth", "login"]}
            }
        }
    ]
    
    # Vehicle Tests
    vehicle_tests = [
        {
            "name": "1. Register Vehicle - CAR",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "NEW-1234", "type": "CAR", "ownerName": "Alex Santos"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/vehicles", "host": ["{{{{baseUrl}}}}"], "path": ["api", "vehicles"]}
            }
        },
        {
            "name": "2. Register Vehicle - MOTORCYCLE",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "MOTO-002", "type": "MOTORCYCLE", "ownerName": "Maria Garcia"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/vehicles", "host": ["{{{{baseUrl}}}}"], "path": ["api", "vehicles"]}
            }
        },
        {
            "name": "3. Register Vehicle - TRUCK",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "TRK-5001", "type": "TRUCK", "ownerName": "Roberto Cruz"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/vehicles", "host": ["{{{{baseUrl}}}}"], "path": ["api", "vehicles"]}
            }
        },
        {
            "name": "4. Register Vehicle - INVALID Type",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "INVALID-001", "type": "BICYCLE", "ownerName": "John Doe"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/vehicles", "host": ["{{{{baseUrl}}}}"], "path": ["api", "vehicles"]}
            }
        },
        {
            "name": "5. Register Vehicle - DUPLICATE",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "ABC-1234", "type": "CAR", "ownerName": "Duplicate Owner"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/vehicles", "host": ["{{{{baseUrl}}}}"], "path": ["api", "vehicles"]}
            }
        },
        {
            "name": "6. Get Vehicle by License Plate",
            "request": {
                "method": "GET",
                "header": [{"key": "Authorization", "value": "Bearer {{token}}"}],
                "url": {"raw": f"{base_url}/vehicles/ABC-1234", "host": ["{{{{baseUrl}}}}"], "path": ["api", "vehicles", "ABC-1234"]}
            }
        },
        {
            "name": "7. Get Non-Existent Vehicle",
            "request": {
                "method": "GET",
                "header": [{"key": "Authorization", "value": "Bearer {{token}}"}],
                "url": {"raw": f"{base_url}/vehicles/NONEXIST-999", "host": ["{{{{baseUrl}}}}"], "path": ["api", "vehicles", "NONEXIST-999"]}
            }
        },
        {
            "name": "8. List All Vehicles",
            "request": {
                "method": "GET",
                "header": [{"key": "Authorization", "value": "Bearer {{token}}"}],
                "url": {"raw": f"{base_url}/vehicles", "host": ["{{{{baseUrl}}}}"], "path": ["api", "vehicles"]}
            }
        }
    ]
    
    # Parking Lot Tests
    lot_tests = [
        {
            "name": "1. Create Parking Lot",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"lotId": "LOT-003", "location": "Quezon City", "capacity": 25}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/lots", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots"]}
            }
        },
        {
            "name": "2. Create Lot - DUPLICATE ID",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"lotId": "LOT-001", "location": "Duplicate", "capacity": 50}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/lots", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots"]}
            }
        },
        {
            "name": "3. Create Lot - INVALID Capacity",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"lotId": "LOT-004", "location": "Invalid", "capacity": 0}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/lots", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots"]}
            }
        },
        {
            "name": "4. List All Parking Lots",
            "request": {
                "method": "GET",
                "header": [{"key": "Authorization", "value": "Bearer {{token}}"}],
                "url": {"raw": f"{base_url}/lots", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots"]}
            }
        },
        {
            "name": "5. Get Specific Lot",
            "request": {
                "method": "GET",
                "header": [{"key": "Authorization", "value": "Bearer {{token}}"}],
                "url": {"raw": f"{base_url}/lots/LOT-001", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots", "LOT-001"]}
            }
        },
        {
            "name": "6. Get Non-Existent Lot",
            "request": {
                "method": "GET",
                "header": [{"key": "Authorization", "value": "Bearer {{token}}"}],
                "url": {"raw": f"{base_url}/lots/LOT-999", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots", "LOT-999"]}
            }
        },
        {
            "name": "7. Check Lot Occupancy",
            "request": {
                "method": "GET",
                "header": [{"key": "Authorization", "value": "Bearer {{token}}"}],
                "url": {"raw": f"{base_url}/lots/LOT-001/occupancy", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots", "LOT-001", "occupancy"]}
            }
        }
    ]
    
    # Parking Session Tests
    session_tests = [
        {
            "name": "1. Check-In CAR",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "ABC-1234"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/lots/LOT-001/check-in", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots", "LOT-001", "check-in"]}
            }
        },
        {
            "name": "2. Check-In MOTORCYCLE",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "MOTO-001"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/lots/LOT-001/check-in", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots", "LOT-001", "check-in"]}
            }
        },
        {
            "name": "3. Check-In TRUCK",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "TRK-2025"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/lots/LOT-001/check-in", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots", "LOT-001", "check-in"]}
            }
        },
        {
            "name": "4. Check-In - Already Parked",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "ABC-1234"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/lots/LOT-001/check-in", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots", "LOT-001", "check-in"]}
            }
        },
        {
            "name": "5. Check-In Non-Existent Vehicle",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "NONEXIST-999"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/lots/LOT-001/check-in", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots", "LOT-001", "check-in"]}
            }
        },
        {
            "name": "6. Check-Out CAR",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "ABC-1234"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/lots/LOT-001/check-out", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots", "LOT-001", "check-out"]}
            }
        },
        {
            "name": "7. Check-Out MOTORCYCLE",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "MOTO-001"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/lots/LOT-001/check-out", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots", "LOT-001", "check-out"]}
            }
        },
        {
            "name": "8. Check-Out TRUCK",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "TRK-2025"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/lots/LOT-001/check-out", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots", "LOT-001", "check-out"]}
            }
        },
        {
            "name": "9. Check-Out Not Parked",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "NOT-PARKED"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/lots/LOT-001/check-out", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots", "LOT-001", "check-out"]}
            }
        },
        {
            "name": "10. List Vehicles in Lot",
            "request": {
                "method": "GET",
                "header": [{"key": "Authorization", "value": "Bearer {{token}}"}],
                "url": {"raw": f"{base_url}/lots/LOT-001/vehicles", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots", "LOT-001", "vehicles"]}
            }
        }
    ]
    
    # Error/Edge Cases
    error_tests = [
        {
            "name": "1. Missing Authorization Header",
            "request": {
                "method": "GET",
                "header": [],
                "url": {"raw": f"{base_url}/vehicles", "host": ["{{{{baseUrl}}}}"], "path": ["api", "vehicles"]}
            }
        },
        {
            "name": "2. Invalid Token",
            "request": {
                "method": "GET",
                "header": [{"key": "Authorization", "value": "Bearer invalid-token-here"}],
                "url": {"raw": f"{base_url}/vehicles", "host": ["{{{{baseUrl}}}}"], "path": ["api", "vehicles"]}
            }
        },
        {
            "name": "3. Malformed JSON",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": "{\n  \"licensePlate\": \"TEST-001\"\n  \"type\": \"CAR\"\n}",
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/vehicles", "host": ["{{{{baseUrl}}}}"], "path": ["api", "vehicles"]}
            }
        },
        {
            "name": "4. Missing Required Fields",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"licensePlate": "TEST-001"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/vehicles", "host": ["{{{{baseUrl}}}}"], "path": ["api", "vehicles"]}
            }
        },
        {
            "name": "5. Invalid Data Type for Capacity",
            "request": {
                "method": "POST",
                "header": [
                    {"key": "Authorization", "value": "Bearer {{token}}"},
                    {"key": "Content-Type", "value": "application/json"}
                ],
                "body": {
                    "mode": "raw",
                    "raw": json.dumps({"lotId": "LOT-005", "location": "Test", "capacity": "not-a-number"}, indent=2),
                    "options": {"raw": {"language": "json"}}
                },
                "url": {"raw": f"{base_url}/lots", "host": ["{{{{baseUrl}}}}"], "path": ["api", "lots"]}
            }
        }
    ]
    
    # Assemble collection
    collection = {
        "info": {
            "name": "SmartPark API - Complete Test Scenarios",
            "_postman_id": "5ef23f7b-9a10-4bb8-b7dd-7cc8f6d4e101",
            "description": "SmartPark API with comprehensive test scenarios, edge cases, and multiple vehicle types",
            "schema": "https://schema.getpostman.com/json/collection/v2.1.0/collection.json"
        },
        "variable": [
            {"key": "baseUrl", "value": "http://localhost:8080"},
            {"key": "token", "value": ""}
        ],
        "item": [
            {
                "name": "🔐 Authentication Tests",
                "description": "Login and authentication tests with valid/invalid credentials",
                "item": auth_tests
            },
            {
                "name": "🚗 Vehicle Tests",
                "description": "Vehicle registration, retrieval, and error scenarios (CAR, MOTORCYCLE, TRUCK)",
                "item": vehicle_tests
            },
            {
                "name": "🅿️ Parking Lot Tests",
                "description": "Lot creation, listing, and occupancy checks",
                "item": lot_tests
            },
            {
                "name": "🔄 Parking Session Tests",
                "description": "Check-in/check-out operations for different vehicle types",
                "item": session_tests
            },
            {
                "name": "⚠️ Error & Edge Case Tests",
                "description": "Authorization errors, malformed requests, and boundary conditions",
                "item": error_tests
            }
        ]
    }
    
    return collection

if __name__ == "__main__":
    collection = generate_collection()
    output_path = "C:\\Git\\SmartPark-Spring-Boot\\smartpark-spring-boot\\postman\\SmartPark_Comprehensive.postman_collection.json"
    
    with open(output_path, 'w', encoding='utf-8') as f:
        json.dump(collection, f, indent=2, ensure_ascii=False)
    
    print(f"✅ Collection generated successfully!")
    print(f"📁 Location: {output_path}")
    print(f"\n📊 Collection Statistics:")
    print(f"   - Authentication Tests: 3")
    print(f"   - Vehicle Tests: 8")
    print(f"   - Parking Lot Tests: 7")
    print(f"   - Parking Session Tests: 10")
    print(f"   - Error/Edge Case Tests: 5")
    print(f"   - Total Endpoints: 33")
