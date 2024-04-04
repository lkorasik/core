FROM node:lts-alpine3.19
EXPOSE 3001
COPY ./ ./
RUN yarn install
ENTRYPOINT ["yarn", "start"]