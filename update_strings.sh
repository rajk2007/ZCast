#!/bin/bash
sed -i 's/NovaCast/ZCast/g' /home/ubuntu/ZCast/app/src/main/res/values/strings.xml
sed -i 's/CloudStream/ZCast/g' /home/ubuntu/ZCast/app/src/main/res/values/strings.xml
sed -i 's/cloudstream/ZCast/g' /home/ubuntu/ZCast/app/src/main/res/values/strings.xml

sed -i 's/NovaCast/ZCast/g' /home/ubuntu/ZCast/app/src/main/res/values/donottranslate-strings.xml
sed -i 's/CloudStream/ZCast/g' /home/ubuntu/ZCast/app/src/main/res/values/donottranslate-strings.xml
sed -i 's/cloudstream/ZCast/g' /home/ubuntu/ZCast/app/src/main/res/values/donottranslate-strings.xml
