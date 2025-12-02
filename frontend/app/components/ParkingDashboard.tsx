'use client';

import { useState } from 'react';
import { Ticket, Receipt, Reservation, ParkingStatus, ParkingSpot } from '@/lib/types';
import {
  parkCar,
  exitCar,
  reserveSpot,
  cancelReservation,
  getParkingStatus,
  getAvailableSpots,
} from '@/lib/api';

export default function ParkingDashboard() {
  // State for different operations
  const [plateNumber, setPlateNumber] = useState('');
  const [ticketId, setTicketId] = useState('');
  const [spotId, setSpotId] = useState('');
  const [userId, setUserId] = useState('');
  const [hours, setHours] = useState('');
  const [reservationId, setReservationId] = useState('');

  // Results state
  const [ticket, setTicket] = useState<Ticket | null>(null);
  const [receipt, setReceipt] = useState<Receipt | null>(null);
  const [reservation, setReservation] = useState<Reservation | null>(null);
  const [parkingStatus, setParkingStatus] = useState<ParkingStatus | null>(null);
  const [availableSpots, setAvailableSpots] = useState<ParkingSpot[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);

  const clearMessages = () => {
    setError(null);
    setSuccessMessage(null);
  };

  const handleParkCar = async () => {
    if (!plateNumber.trim()) {
      setError('Please enter a plate number');
      return;
    }
    clearMessages();
    setLoading(true);
    try {
      const result = await parkCar(plateNumber);
      setTicket(result);
      setPlateNumber('');
      setSuccessMessage(`Car parked successfully! Ticket #${result.ticketId}, Spot: ${result.spotId}`);
    } catch {
      setError('Failed to park car. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleExitCar = async () => {
    if (!ticketId.trim()) {
      setError('Please enter a ticket ID');
      return;
    }
    clearMessages();
    setLoading(true);
    try {
      const result = await exitCar(parseInt(ticketId));
      setReceipt(result);
      setTicketId('');
      setSuccessMessage(`Exit successful! Amount: $${result.amount.toFixed(2)}`);
    } catch {
      setError('Failed to exit car. Please check the ticket ID.');
    } finally {
      setLoading(false);
    }
  };

  const handleReserveSpot = async () => {
    if (!spotId.trim() || !userId.trim() || !hours.trim()) {
      setError('Please fill in all reservation fields');
      return;
    }
    clearMessages();
    setLoading(true);
    try {
      const result = await reserveSpot(parseInt(spotId), userId, parseInt(hours));
      setReservation(result);
      setSpotId('');
      setUserId('');
      setHours('');
      setSuccessMessage(`Reservation successful! Reserved until ${new Date(result.reservedUntil).toLocaleString()}`);
    } catch {
      setError('Failed to reserve spot. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleCancelReservation = async () => {
    if (!reservationId.trim()) {
      setError('Please enter a reservation ID');
      return;
    }
    clearMessages();
    setLoading(true);
    try {
      await cancelReservation(parseInt(reservationId));
      setReservationId('');
      setSuccessMessage('Reservation cancelled successfully!');
      setReservation(null);
    } catch {
      setError('Failed to cancel reservation. Please check the reservation ID.');
    } finally {
      setLoading(false);
    }
  };

  const handleGetParkingStatus = async () => {
    clearMessages();
    setLoading(true);
    try {
      const result = await getParkingStatus();
      setParkingStatus(result);
    } catch {
      setError('Failed to get parking status.');
    } finally {
      setLoading(false);
    }
  };

  const handleGetAvailableSpots = async () => {
    clearMessages();
    setLoading(true);
    try {
      const result = await getAvailableSpots();
      setAvailableSpots(result);
    } catch {
      setError('Failed to get available spots.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-gray-100 py-8 px-4">
      <div className="max-w-6xl mx-auto">
        <h1 className="text-4xl font-bold text-center text-gray-800 mb-8">
          🚗 Smart Parking Management System
        </h1>

        {/* Error and Success Messages */}
        {error && (
          <div className="mb-4 p-4 bg-red-100 border border-red-400 text-red-700 rounded-lg">
            {error}
          </div>
        )}
        {successMessage && (
          <div className="mb-4 p-4 bg-green-100 border border-green-400 text-green-700 rounded-lg">
            {successMessage}
          </div>
        )}

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {/* Park Car Section */}
          <div className="bg-white rounded-lg shadow-lg p-6">
            <h2 className="text-xl font-semibold text-gray-700 mb-4">🅿️ Park Car</h2>
            <input
              type="text"
              placeholder="Enter Plate Number"
              value={plateNumber}
              onChange={(e) => setPlateNumber(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-lg mb-4 focus:outline-none focus:ring-2 focus:ring-blue-500"
            />
            <button
              onClick={handleParkCar}
              disabled={loading}
              className="w-full bg-blue-500 text-white py-3 rounded-lg hover:bg-blue-600 transition disabled:bg-gray-400"
            >
              {loading ? 'Processing...' : 'Park Car'}
            </button>
            {ticket && (
              <div className="mt-4 p-3 bg-blue-50 rounded-lg">
                <p className="text-sm text-gray-600">Ticket ID: <span className="font-bold">{ticket.ticketId}</span></p>
                <p className="text-sm text-gray-600">Spot: <span className="font-bold">{ticket.spotId}</span></p>
                <p className="text-sm text-gray-600">Plate: <span className="font-bold">{ticket.plateNumber}</span></p>
              </div>
            )}
          </div>

          {/* Exit Car Section */}
          <div className="bg-white rounded-lg shadow-lg p-6">
            <h2 className="text-xl font-semibold text-gray-700 mb-4">🚪 Exit Car</h2>
            <input
              type="number"
              placeholder="Enter Ticket ID"
              value={ticketId}
              onChange={(e) => setTicketId(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-lg mb-4 focus:outline-none focus:ring-2 focus:ring-green-500"
            />
            <button
              onClick={handleExitCar}
              disabled={loading}
              className="w-full bg-green-500 text-white py-3 rounded-lg hover:bg-green-600 transition disabled:bg-gray-400"
            >
              {loading ? 'Processing...' : 'Exit'}
            </button>
            {receipt && (
              <div className="mt-4 p-3 bg-green-50 rounded-lg">
                <p className="text-sm text-gray-600">Receipt ID: <span className="font-bold">{receipt.receiptId}</span></p>
                <p className="text-sm text-gray-600">Duration: <span className="font-bold">{receipt.duration}h</span></p>
                <p className="text-sm text-gray-600">Amount: <span className="font-bold text-green-600">${receipt.amount.toFixed(2)}</span></p>
              </div>
            )}
          </div>

          {/* Reserve Spot Section */}
          <div className="bg-white rounded-lg shadow-lg p-6">
            <h2 className="text-xl font-semibold text-gray-700 mb-4">📅 Reserve Spot</h2>
            <input
              type="number"
              placeholder="Spot ID"
              value={spotId}
              onChange={(e) => setSpotId(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-lg mb-2 focus:outline-none focus:ring-2 focus:ring-purple-500"
            />
            <input
              type="text"
              placeholder="User ID"
              value={userId}
              onChange={(e) => setUserId(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-lg mb-2 focus:outline-none focus:ring-2 focus:ring-purple-500"
            />
            <input
              type="number"
              placeholder="Hours"
              value={hours}
              onChange={(e) => setHours(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-lg mb-4 focus:outline-none focus:ring-2 focus:ring-purple-500"
            />
            <button
              onClick={handleReserveSpot}
              disabled={loading}
              className="w-full bg-purple-500 text-white py-3 rounded-lg hover:bg-purple-600 transition disabled:bg-gray-400"
            >
              {loading ? 'Processing...' : 'Reserve'}
            </button>
            {reservation && (
              <div className="mt-4 p-3 bg-purple-50 rounded-lg">
                <p className="text-sm text-gray-600">Reservation ID: <span className="font-bold">{reservation.reservationId}</span></p>
                <p className="text-sm text-gray-600">Spot: <span className="font-bold">{reservation.spotId}</span></p>
                <p className="text-sm text-gray-600">Until: <span className="font-bold">{new Date(reservation.reservedUntil).toLocaleString()}</span></p>
              </div>
            )}
          </div>

          {/* Cancel Reservation Section */}
          <div className="bg-white rounded-lg shadow-lg p-6">
            <h2 className="text-xl font-semibold text-gray-700 mb-4">❌ Cancel Reservation</h2>
            <input
              type="number"
              placeholder="Reservation ID"
              value={reservationId}
              onChange={(e) => setReservationId(e.target.value)}
              className="w-full p-3 border border-gray-300 rounded-lg mb-4 focus:outline-none focus:ring-2 focus:ring-red-500"
            />
            <button
              onClick={handleCancelReservation}
              disabled={loading}
              className="w-full bg-red-500 text-white py-3 rounded-lg hover:bg-red-600 transition disabled:bg-gray-400"
            >
              {loading ? 'Processing...' : 'Cancel Reservation'}
            </button>
          </div>

          {/* Parking Status Section */}
          <div className="bg-white rounded-lg shadow-lg p-6">
            <h2 className="text-xl font-semibold text-gray-700 mb-4">📊 Parking Status</h2>
            <button
              onClick={handleGetParkingStatus}
              disabled={loading}
              className="w-full bg-indigo-500 text-white py-3 rounded-lg hover:bg-indigo-600 transition disabled:bg-gray-400 mb-4"
            >
              {loading ? 'Loading...' : 'View Status'}
            </button>
            {parkingStatus && (
              <div className="p-4 bg-indigo-50 rounded-lg">
                <div className="grid grid-cols-2 gap-2">
                  <p className="text-sm text-gray-600">Total Spots:</p>
                  <p className="text-sm font-bold text-right">{parkingStatus.totalSpots}</p>
                  <p className="text-sm text-gray-600">Occupied:</p>
                  <p className="text-sm font-bold text-right text-red-600">{parkingStatus.occupiedSpots}</p>
                  <p className="text-sm text-gray-600">Available:</p>
                  <p className="text-sm font-bold text-right text-green-600">{parkingStatus.availableSpots}</p>
                  <p className="text-sm text-gray-600">Reservations:</p>
                  <p className="text-sm font-bold text-right text-purple-600">{parkingStatus.totalReservations}</p>
                </div>
                <div className="mt-3 pt-3 border-t border-indigo-200">
                  <p className="text-center text-lg font-semibold text-indigo-700">
                    Occupancy: {((parkingStatus.occupiedSpots / parkingStatus.totalSpots) * 100).toFixed(1)}%
                  </p>
                </div>
              </div>
            )}
          </div>

          {/* Available Spots Section */}
          <div className="bg-white rounded-lg shadow-lg p-6">
            <h2 className="text-xl font-semibold text-gray-700 mb-4">🟢 Available Spots</h2>
            <button
              onClick={handleGetAvailableSpots}
              disabled={loading}
              className="w-full bg-teal-500 text-white py-3 rounded-lg hover:bg-teal-600 transition disabled:bg-gray-400 mb-4"
            >
              {loading ? 'Loading...' : 'Show Available'}
            </button>
            {availableSpots.length > 0 && (
              <div className="max-h-48 overflow-y-auto">
                <div className="grid grid-cols-3 gap-2">
                  {availableSpots.slice(0, 15).map((spot) => (
                    <div
                      key={spot.spotId}
                      className="p-2 bg-teal-50 rounded text-center text-sm"
                    >
                      <span className="font-bold">{spot.spotNumber}</span>
                    </div>
                  ))}
                </div>
                {availableSpots.length > 15 && (
                  <p className="text-sm text-gray-500 mt-2 text-center">
                    +{availableSpots.length - 15} more spots
                  </p>
                )}
              </div>
            )}
          </div>
        </div>

        <footer className="mt-8 text-center text-gray-500 text-sm">
          <p>Smart Parking Management System - Command Pattern + MVC Architecture</p>
        </footer>
      </div>
    </div>
  );
}
