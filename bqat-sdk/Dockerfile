FROM openjdk:11

ARG bqatsdk_zip_url

ENV bqatsdk_zip_file_path=${bqatsdk_zip_url}

ARG bqatsdk_local_dir=bqatsdk-client

ENV bqatsdk_local_dir_name=${bqatsdk_local_dir}

ARG sdk_impl

ARG service_context=/bqatsdk-service

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG spring_config_label

# can be passed during Docker build as build time environment for spring profiles active 
ARG active_profile

# can be passed during Docker build as build time environment for config server URL 
ARG spring_config_url

ENV service_context_env=${service_context}

#ENV bqatsdk_zip_url=$bqatsdk_zip_url
ENV bqatsdk_bioapi_impl=${sdk_impl}

# environment variable to pass active profile such as DEV, QA etc at docker runtime
ENV active_profile_env=${active_profile}

# environment variable to pass spring cloud config uri such as DEV, QA etc at docker runtime
<<<<<<< Updated upstream
ENV spring_config_label_env=${spring_config_label}
=======
ENV spring_config_url_env=${spring_config_url}
>>>>>>> Stashed changes

# environment variable to pass github branch to pickup configuration from, at docker runtime
ENV spring_config_url_env=${spring_config_url}

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG container_user=mosip

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG container_user_group=mosip

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG container_user_uid=1001

# can be passed during Docker build as build time environment for github branch to pickup configuration from.
ARG container_user_gid=1001

# install packages and create user
RUN apt-get -y update \
&& apt-get install -y unzip sudo\
&& groupadd -g ${container_user_gid} ${container_user_group} \
&& useradd -u ${container_user_uid} -g ${container_user_group} -s /bin/sh -m ${container_user}

# set working directory for the user
WORKDIR /home/${container_user}

ENV work_dir=/home/${container_user}

ARG loader_path=${work_dir}/additional_jars/

RUN mkdir -p ${loader_path}

ENV loader_path_env=${loader_path}

ARG logging_level_root=INFO

ENV logging_level_root_env=${logging_level_root}

ADD ./target/bqatsdk-services-*.jar bqatsdk-services.jar

ADD ./configure_bqatsdk.sh configure_bqatsdk.sh

RUN chmod 775 bqatsdk-services.jar

RUN chmod +x configure_bqatsdk.sh

# change permissions of file inside working dir
RUN chown -R ${container_user}:${container_user} /home/${container_user}

# select container user for all tasks
USER ${container_user_uid}:${container_user_gid}

EXPOSE 8849

#ENTRYPOINT [ "./configure_bqatsdk.sh" ]

CMD echo $bqatsdk_bioapi_impl ; \
<<<<<<< Updated upstream
java -Dloader.path="${loader_path_env}" -Dbqatsdk_bioapi_impl="${bqatsdk_bioapi_impl}" -Dspring.cloud.config.label="${spring_config_label_env}" -Dspring.profiles.active="${active_profile_env}" -Dspring.cloud.config.uri="${spring_config_url_env}"  -jar bqatsdk-services.jar
=======
java -Dloader.path="${loader_path_env}" -Dbqatsdk_bioapi_impl="${bqatsdk_bioapi_impl}" -Dspring.cloud.config.label="${spring_config_label_env}" -Dspring.profiles.active="${active_profile_env}" -Dspring.cloud.config.uri="${spring_config_url_env}" -jar bqatsdk-services.jar
>>>>>>> Stashed changes
