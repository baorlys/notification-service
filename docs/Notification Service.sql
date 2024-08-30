CREATE TABLE [user_info] (
  [id] UUID PRIMARY KEY,
  [email] VARCHAR UNIQUE NOT NULL,
  [phone] VARCHAR,
  [name] VARCHAR
)
GO

CREATE TABLE [credential] (
  [id] UUID PRIMARY KEY,
  [user_id] uuid,
  [api_key] nvarchar(255),
  [secret_key] nvarchar(255)
)
GO

CREATE TABLE [templateMessage] (
  [id] UUID PRIMARY KEY,
  [owner] UUID,
  [name] VARCHAR,
  [type] enum(plain_text,html),
  [target_output] ENUM(email,sms,voice,media,push,social) NOT NULL,
  [content] TEXT
)
GO

CREATE TABLE [notification] (
  [id] UUID PRIMARY KEY,
  [user_id] UUID,
  [sender] nvarchar(255),
  [template_id] UUID,
  [status] ENUM(pending,sent,delivered,failed) NOT NULL,
  [target_output] ENUM(email,sms,voice,media,fb,linkedin) NOT NULL
)
GO

CREATE TABLE [receiver] (
  [id] UUID PRIMARY KEY,
  [notification_id] UUID,
  [contact] nvarchar(255),
  [data] JSON,
  [is_read] bit
)
GO

CREATE TABLE [log] (
  [id] UUID PRIMARY KEY,
  [notification_id] UUID,
  [queue_name] VARCHAR,
  [target_output] ENUM(email,sms,voice,media,push,social) NOT NULL,
  [status] ENUM(queued,processing,completed,failed) NOT NULL
)
GO

CREATE TABLE [webhook] (
  [id] UUID PRIMARY KEY,
  [user_id] UUID,
  [url] VARCHAR,
  [event_type] ENUM(DELIVERY_STATUS,READ_STATUS,CLICK_STATUS) NOT NULL
)
GO

CREATE TABLE [scheduled_notification] (
  [id] UUID PRIMARY KEY,
  [notification_id] UUID,
  [trigger_condition] JSON
)
GO

ALTER TABLE [credential] ADD FOREIGN KEY ([user_id]) REFERENCES [user_info] ([id])
GO

ALTER TABLE [templateMessage] ADD FOREIGN KEY ([owner]) REFERENCES [user_info] ([id])
GO

ALTER TABLE [notification] ADD FOREIGN KEY ([user_id]) REFERENCES [user_info] ([id])
GO

ALTER TABLE [notification] ADD FOREIGN KEY ([template_id]) REFERENCES [templateMessage] ([id])
GO

ALTER TABLE [receiver] ADD FOREIGN KEY ([notification_id]) REFERENCES [notification] ([id])
GO

ALTER TABLE [log] ADD FOREIGN KEY ([notification_id]) REFERENCES [notification] ([id])
GO

ALTER TABLE [webhook] ADD FOREIGN KEY ([user_id]) REFERENCES [user_info] ([id])
GO

ALTER TABLE [scheduled_notification] ADD FOREIGN KEY ([notification_id]) REFERENCES [notification] ([id])
GO

ALTER TABLE [log] ADD FOREIGN KEY ([id]) REFERENCES [log] ([queue_name])
GO

ALTER TABLE [log] ADD FOREIGN KEY ([id]) REFERENCES [log] ([notification_id])
GO
