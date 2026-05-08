import {defineConfig} from "vite"
import tailwindcss from "@tailwindcss/vite"

export default defineConfig({
    root: "kotlin",
    base: "/random-reaper/",
    plugins: [tailwindcss()],
    server: {
        host: '0.0.0.0', // or host: true
    },
})
