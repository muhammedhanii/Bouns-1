# Smart Parking Management System

A full-stack application implementing a Smart Parking Management System using the **Command Pattern** and **MVC Architecture**.

## Architecture

This system follows the design patterns specified in the UML diagrams:
- **Command Pattern**: All operations are encapsulated as commands (ParkCarCommand, ExitCarCommand, ReserveSpotCommand, CancelReservationCommand, GetParkingStatusCommand)
- **MVC Architecture**: Clear separation between View (Next.js), Controller (Spring Boot REST), and Model layers

## Project Structure

```
├── backend/                    # Spring Boot Backend
│   ├── src/main/java/com/parking/smartparking/
│   │   ├── command/           # Command Pattern implementation
│   │   │   ├── Command.java
│   │   │   ├── CommandInvoker.java
│   │   │   ├── ParkCarCommand.java
│   │   │   ├── ExitCarCommand.java
│   │   │   ├── ReserveSpotCommand.java
│   │   │   ├── CancelReservationCommand.java
│   │   │   └── GetParkingStatusCommand.java
│   │   ├── controller/        # REST Controllers
│   │   │   └── ParkingController.java
│   │   ├── hardware/          # Hardware interfaces
│   │   │   ├── HardwareReceiver.java
│   │   │   ├── ParkingGate.java
│   │   │   ├── ParkingSensor.java
│   │   │   └── LEDDisplay.java
│   │   ├── model/             # Domain Models
│   │   │   ├── Car.java
│   │   │   ├── ParkingSpot.java
│   │   │   ├── Ticket.java
│   │   │   ├── Reservation.java
│   │   │   ├── Receipt.java
│   │   │   └── ParkingStatus.java
│   │   ├── repository/        # Data Repositories
│   │   │   ├── IParkingRepository.java
│   │   │   └── ParkingRepository.java
│   │   ├── service/           # Business Services
│   │   │   ├── IParkingService.java
│   │   │   └── ParkingService.java
│   │   └── config/            # Configuration
│   │       └── CorsConfig.java
│   └── pom.xml
│
└── frontend/                   # Next.js Frontend
    ├── app/
    │   ├── components/
    │   │   └── ParkingDashboard.tsx
    │   ├── layout.tsx
    │   └── page.tsx
    └── lib/
        ├── api.ts             # API client
        └── types.ts           # TypeScript types
```

## Features

1. **Park Car** - Enter plate number to park a car and receive a ticket
2. **Exit Car** - Enter ticket ID to exit and receive a receipt with payment
3. **Reserve Spot** - Reserve a specific parking spot for a duration
4. **Cancel Reservation** - Cancel an existing reservation
5. **View Status** - See overall parking lot status (occupancy, available spots)
6. **Available Spots** - View list of currently available parking spots

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/parking/park | Park a car |
| POST | /api/parking/exit | Exit a car |
| POST | /api/parking/reserve | Reserve a spot |
| DELETE | /api/parking/reservation/{id} | Cancel a reservation |
| GET | /api/parking/status | Get parking status |
| GET | /api/parking/spots/available | Get available spots |

## Running the Application

### Backend (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### Frontend (Next.js)

```bash
cd frontend
npm install
npm run dev
```

The frontend will start on `http://localhost:3000`

## Technologies

- **Backend**: Java 17, Spring Boot 3.2, Maven
- **Frontend**: Next.js 16, TypeScript, Tailwind CSS
- **Patterns**: Command Pattern, MVC Architecture
