"use client"


import axios from "axios";

export default function Test (){

}
export const client = axios.create({
    baseURL: "http://127.0.0.1:3000",
});

client.interceptors.request.use((config) => {
    const auth_header = config.headers["x-auth-not-required"];
    if (auth_header) return config;

    // config.headers["Authorization"] = `Bearer ${token}`;

    return config;
});