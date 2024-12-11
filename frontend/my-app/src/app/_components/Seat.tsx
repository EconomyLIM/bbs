"use client";

import { useEffect, useState } from 'react';
import axios from 'axios';
import SockJS from 'sockjs-client';
import { CompatClient, Stomp } from '@stomp/stompjs';

interface Seat {
    id: number;
    occupied: boolean;
    seatNumber: number;
}

export function Seat() {
    const [seats, setSeats] = useState<Seat[]>([]);

    useEffect(() => {
        const fetchSeats = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/seats');
                setSeats(response.data);
            } catch (error) {
                console.error('Error fetching seats:', error);
            }
        };

        fetchSeats();

        // WebSocket 연결 설정
        const socket = new SockJS('http://localhost:8080/ws');
        const stompClient: CompatClient = Stomp.over(socket);

        stompClient.connect({}, () => {
            console.log('Connected to WebSocket server');
            stompClient.subscribe('/topic/seats', (message) => {
                const updatedSeat: Seat = JSON.parse(message.body);
                console.log('WebSocket message received:', updatedSeat);

                // 상태 갱신 로직
                setSeats((prevSeats) => {
                    const updatedSeats = prevSeats.map((seat) =>
                        seat.id === updatedSeat.id ? { ...seat, occupied: updatedSeat.occupied } : seat
                    );
                    console.log('Updated seats:', updatedSeats);
                    return updatedSeats;
                });
            });
        });

        return () => {
            if (stompClient) {
                stompClient.disconnect();
                console.log('Disconnected from WebSocket server');
            }
        };
    }, []);

    const handleOccupy = async (id: number) => {
        const confirmReservation = window.confirm('예약하겠습니까?');
        if (confirmReservation) {
            try {
                await axios.put(`http://localhost:8080/api/seats/${id}/occupy`);
                alert('좌석이 예약되었습니다.');
            } catch (error) {
                console.error('Error occupying seat:', error);
            }
        }
    };

    const handleRelease = async (id: number) => {
        try {
            await axios.put(`http://localhost:8080/api/seats/${id}/release`);
        } catch (error) {
            console.error('Error releasing seat:', error);
        }
    };

    return (
        <div
            className="seat-container"
            style={{ display: 'grid', gridTemplateColumns: 'repeat(5, 1fr)', gap: '10px' }}
        >
            {seats.slice(0, 20).map((seat) => (
                <div
                    key={seat.id}
                    style={{
                        padding: '20px',
                        backgroundColor: seat.occupied ? 'red' : 'green',
                        color: 'white',
                        textAlign: 'center',
                        cursor: 'pointer',
                    }}
                    onClick={() => (seat.occupied ? handleRelease(seat.id) : handleOccupy(seat.id))}
                >
                    Seat {seat.seatNumber}: {seat.occupied ? 'Occupied (Click to release)' : 'Available (Click to occupy)'}
                </div>
            ))}
        </div>
    );
}
