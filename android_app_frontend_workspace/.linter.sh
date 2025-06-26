#!/bin/bash
cd /home/kavia/workspace/code-generation/habittrackr-71241-0d4e8885/android_app_frontend_workspace/android_app_frontend
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

