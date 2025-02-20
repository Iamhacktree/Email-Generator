# Email Generator

This is a simple Email Generator application built with Spring Boot for the backend and React (with MUI) for the frontend. It utilizes Gemini AI to generate email content.

## Features
- Generate AI-powered email content
- User-friendly React UI with MUI components
- No database required

## Tech Stack
- **Frontend:** React, MUI (Material-UI)
- **Backend:** Spring Boot
- **AI API:** Gemini AI

## Installation and Setup

### Backend (Spring Boot)

1. Clone the repository:
   ```sh
   git clone https://github.com/your-repo/email-generator.git
   cd email-generator/backend
   ```
2. Set up environment variables:
   - `GEMINI_URL`: Your Gemini AI API endpoint
   - `GEMINI_API`: Your Gemini AI API key
3. Run the Spring Boot application:
   ```sh
   mvn spring-boot:run
   ```

### Frontend (React)

1. Navigate to the frontend directory:
   ```sh
   cd ../frontend
   ```
2. Install dependencies:
   ```sh
   npm install
   ```
3. Start the development server:
   ```sh
   npm start
   ```
### Screenshot
![image](https://github.com/user-attachments/assets/86ea9865-7ad4-4a85-ae56-29b0f0fe27b8)

## Usage
1. Open the React app in the browser (default: `http://localhost:5173`).
2. Enter the required details and generate email content using Gemini AI.
3. The generated email will be displayed in the UI.

## Environment Variables
Ensure the following environment variables are set in your application:
```sh
GEMINI_URL=https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent
GEMINI_API=your-api-key-here
```

## Contributing
Feel free to contribute by submitting issues or pull requests!


