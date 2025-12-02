import { Ticket, Receipt, Reservation, ParkingStatus, ParkingSpot } from './types';

const API_BASE_URL = 'http://localhost:8080/api/parking';

export async function parkCar(plateNumber: string): Promise<Ticket> {
  const response = await fetch(`${API_BASE_URL}/park`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ plateNumber }),
  });

  if (!response.ok) {
    throw new Error('Failed to park car');
  }

  return response.json();
}

export async function exitCar(ticketId: number): Promise<Receipt> {
  const response = await fetch(`${API_BASE_URL}/exit`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ ticketId }),
  });

  if (!response.ok) {
    throw new Error('Failed to exit car');
  }

  return response.json();
}

export async function reserveSpot(spotId: number, userId: string, hours: number): Promise<Reservation> {
  const response = await fetch(`${API_BASE_URL}/reserve`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ spotId, userId, hours }),
  });

  if (!response.ok) {
    throw new Error('Failed to reserve spot');
  }

  return response.json();
}

export async function cancelReservation(reservationId: number): Promise<boolean> {
  const response = await fetch(`${API_BASE_URL}/reservation/${reservationId}`, {
    method: 'DELETE',
  });

  if (!response.ok) {
    throw new Error('Failed to cancel reservation');
  }

  return response.json();
}

export async function getParkingStatus(): Promise<ParkingStatus> {
  const response = await fetch(`${API_BASE_URL}/status`);

  if (!response.ok) {
    throw new Error('Failed to get parking status');
  }

  return response.json();
}

export async function getAvailableSpots(): Promise<ParkingSpot[]> {
  const response = await fetch(`${API_BASE_URL}/spots/available`);

  if (!response.ok) {
    throw new Error('Failed to get available spots');
  }

  return response.json();
}
