$Neo4jVersion = "3.3.0"

# Set the working directory to the parent directory
Set-Location (Join-Path (Split-Path $MyInvocation.MyCommand.Path) "..\")

# Remove the existing neo4j-server directory
Remove-Item -Recurse -Force neo4j-server

# Download Neo4j
Invoke-WebRequest -Uri "https://neo4j.com/artifact.php?name=neo4j-community-$Neo4jVersion-windows.zip" -OutFile "neo4j.zip"

# Extract Neo4j
Expand-Archive -Path "neo4j.zip" -DestinationPath .

# Rename the extracted folder
Rename-Item -Path "neo4j-community-$Neo4jVersion" -NewName "neo4j-server"

# Clean up the zip file
Remove-Item -Path "neo4j.zip"
