@echo off
echo Creating Pure Android App (No React Native)...
echo.

echo Step 1: Creating assets folder...
mkdir AndroidStudioProject\app\src\main\assets 2>nul

echo Step 2: Copying HTML file...
copy index.html AndroidStudioProject\app\src\main\assets\

echo.
echo ✅ Done!
echo.
echo NOW OPEN IN ANDROID STUDIO:
echo 1. Open Android Studio
echo 2. Click "Open"
echo 3. Select the "AndroidStudioProject" folder
echo 4. Wait for Gradle sync
echo 5. Click Run
echo.
pause
