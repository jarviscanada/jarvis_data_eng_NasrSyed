#! /bin/bash

#Setup initial CLI arguments (must be 5 inputs)
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

#Verify # of arguments
if [ $# -ne  5 ]; then
  echo 'Incorrect number of arguments. Enter:host,port,db name,user,password'
  exit 1
fi

#Saving variables for machine stats(MB) and current machine hostname
hostname=$(hostname -f)
lscpu_out=$(lscpu)


#Retrieve hardware specification variables
cpu_number=$(echo "$lscpu_out" | egrep "^CPU\(s\):" | awk '{print $2}' | xargs)
cpu_architecture=$(echo "$lscpu_out" | grep "Architecture" | xargs)
cpu_model=$(echo "$lscpu_out" | grep "Model name" | xargs)
cpu_mhz=$(echo "$lscpu_out" | grep "CPU MHz" | awk '{print $3}' | xargs)
l2_cache=$(echo "$lscpu_out" | grep "L2" | awk '{print $3}' | sed 's/K//' | xargs)
total_mem=$(cat /proc/meminfo | grep "MemTotal" | awk '{print $2}' | xargs)

#Current time in `2019-11-26 14:40:19` UTC format
timestamp=$(date +"%Y-%m-%d %T")

#Subquery to find matching id in host_info table
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

#PSQL command: Inserts server usage data into host_usage table
#Note: be careful with double and single quotes
insert_stmt="INSERT INTO host_info(
hostname,
cpu_number,
cpu_architecture,
cpu_model,
cpu_mhz,
l2_cache,
total_mem,
timestamp
)
VALUES(
'$hostname',
 $cpu_number,
'$cpu_architecture',
'$cpu_model',
 $cpu_mhz,
 $l2_cache,
 $total_mem,
'$timestamp'
);"

#set up env var for pql cmd
export PGPASSWORD=$psql_password
#Insert date into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?