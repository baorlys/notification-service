INSERT INTO [user_info] ([id], [email], [phone], [name])
VALUES
  (NEWID(), 'alice@example.com', '123-456-7890', 'Alice Johnson'),
  (NEWID(), 'bob@example.com', '234-567-8901', 'Bob Smith');


INSERT INTO [credential] ([id], [user_id], [api_key], [secret_key])
VALUES
  (NEWID(), (SELECT [id] FROM [user_info] WHERE [email] = 'alice@example.com'), 'api_key_1', 'secret_key_1'),
  (NEWID(), (SELECT [id] FROM [user_info] WHERE [email] = 'bob@example.com'), 'api_key_2', 'secret_key_2');


INSERT INTO [templateMessage] ([id], [owner_id], [name], [template_type], [body])
VALUES
  (NEWID(), (SELECT [id] FROM [user_info] WHERE [email] = 'alice@example.com'), 'Welcome Email', 'plain_text', 'Welcome to our service!'),
  (NEWID(), (SELECT [id] FROM [user_info] WHERE [email] = 'bob@example.com'), 'Account Verification', 'html', '<html><body>Verify your account.</body></html>');

INSERT INTO [notification] ([id], [user_id], [sender], [template_id], [status], [target_output])
VALUES
  (NEWID(), (SELECT [id] FROM [user_info] WHERE [email] = 'alice@example.com'), 'System', (SELECT [id] FROM [templateMessage] WHERE [name] = 'Welcome Email'), 'pending', 'email'),
  (NEWID(), (SELECT [id] FROM [user_info] WHERE [email] = 'bob@example.com'), 'Admin', (SELECT [id] FROM [templateMessage] WHERE [name] = 'Account Verification'), 'sent', 'sms');

-- Insert sample data into receiver table
INSERT INTO recipient ([id], [notification_id], [contact], [data], [is_read])
VALUES
  (NEWID(), (SELECT [id] FROM [notification] WHERE [target_output] = 'email'), 'alice@example.com', '{"status": "delivered"}', 0),
  (NEWID(), (SELECT [id] FROM [notification] WHERE [target_output] = 'sms'), 'bob@example.com', '{"status": "read"}', 0);

INSERT INTO [log] ([id], [notification_id], [queue], [status])
VALUES
  (NEWID(), (SELECT [id] FROM [notification] WHERE [target_output] = 'email'), 'queue_1',  'completed'),
  (NEWID(), (SELECT [id] FROM [notification] WHERE [target_output] = 'sms'), 'queue_2', 'processing');

INSERT INTO [webhook] ([id], [user_id], [url], event)
VALUES
  (NEWID(), (SELECT [id] FROM [user_info] WHERE [email] = 'alice@example.com'), 'http://example.com/webhook', 'DELIVERY_STATUS'),
  (NEWID(), (SELECT [id] FROM [user_info] WHERE [email] = 'bob@example.com'), 'http://example.com/webhook', 'CLICK_STATUS');

INSERT INTO [schedule_notification] ([id], [notification_id], [trigger_condition])
VALUES
  (NEWID(), (SELECT [id] FROM [notification] WHERE [target_output] = 'email'), '{"time": "2024-08-21T09:00:00Z"}'),
  (NEWID(), (SELECT [id] FROM [notification] WHERE [target_output] = 'sms'), '{"day": "Monday"}');