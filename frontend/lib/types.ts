// Types for the Smart Parking Management System

export interface Car {
  carId: number;
  plateNumber: string;
  ownerName: string;
}

export interface ParkingSpot {
  spotId: number;
  spotNumber: string;
  isOccupied: boolean;
  parkedCar: Car | null;
}

export interface Ticket {
  ticketId: number;
  plateNumber: string;
  spotId: number;
  entryTime: string;
}

export interface Reservation {
  reservationId: number;
  spotId: number;
  userId: string;
  reservedUntil: string;
}

export interface Receipt {
  receiptId: number;
  ticketId: number;
  duration: number;
  amount: number;
  exitTime: string;
}

export interface ParkingStatus {
  totalSpots: number;
  occupiedSpots: number;
  availableSpots: number;
  totalReservations: number;
}
