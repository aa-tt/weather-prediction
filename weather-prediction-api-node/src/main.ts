import { NestFactory } from '@nestjs/core';
import { ConfigService } from '@nestjs/config';
import { AppModule } from './app.module';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  const configService = app.get(ConfigService);

  app.enableCors({
    origin: configService.get('ALLOW_ORIGINS'),
  });

  await app.listen(8080);
  console.log(`Weather Prediction API running at http://localhost:8080`);
}
bootstrap();