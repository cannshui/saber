#!/usr/bin/env bash
# dn 2020/01/18

set -o errexit

backup_name=saber-$(date +%s)
backup_path=/var/backup/${backup_name}

mkdir -p ${backup_path}
cd /var/backup

cp -a /var/apps/saber/WEB-INF/view/20* ${backup_path}
cp /var/apps/data/saber.db ${backup_path}

tar -zvcf ${backup_name}.tar.gz ${backup_name}

# clear
rm -rf ${backup_name}

echo Succeed to backup, file: /var/backup/${backup_name}.tar.gz