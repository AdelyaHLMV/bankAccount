@echo off

SetLocal EnableDelayedExpansion

for /L %%B in (1,1,100) do (
    timeout 1
    set number=%%B
    if exist account.py echo 'Start account.py with number !number!'
    start /b python account.py !number! 5 200 <NUL
)

SetLocal DisableDelayedExpansion
