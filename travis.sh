#!/bin/bash

if ! [[ -d academic ]] ; then
    exit 0
fi

cd academic
echo -e "\nAcademic difference\n"

for i in `ls -d */`
do
    i=${i%"/"}
    cd $i
    
    if mvn clean compile >/dev/null 2>/dev/null ; then
        echo "Task ${i:4}: Build passing..."
    else
        echo "Task ${i:4}: Build failing!"
        exit 1
    fi

    if mvn clean test >/dev/null 2>/dev/null ; then
        echo "Task ${i:4}: Test passing..."
    else
        echo "Task ${i:4}: Test failing!"
        exit 1
    fi
    
    cd ..
    echo ""
done

exit 0
