#!/bin/bash
fl=$1
start_date=$2
end_date=$3
if [ $# -lt 2 ]
then
        echo "ERROR Parameter!At least two parameter "
        exit
elif [ $# -eq 2 ]
then
        stat_date=`date -d "$start_date" +%Y-%m-%d`
        echo $stat_date
        `${fl}.sh ${stat_date}`
elif [ $# -eq 3 ]
then
while [ "$start_date" -le "$end_date" ]
do
  stat_date=`date -d "$start_date" +%Y-%m-%d`
  echo $stat_date
  `${fl}.sh ${stat_date}`
  start_date=$(date -d "$start_date+1days" +%Y%m%d)
done
else
        echo "ERROR Parameter!At most three parameter "
        exit
fi
