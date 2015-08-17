CREATE TABLE DGB_ACCESS_LOG (
ACCLOG_REQUEST_URI VARCHAR(256),
ACCLOG_REMOTE_USER VARCHAR(128),
ACCLOG_REMOTE_HOST VARCHAR(128),
ACCLOG_SESSION_ID VARCHAR(64),
ACCLOG_METHOD VARCHAR(12),
ACCLOG_USER_AGENT VARCHAR(32),
ACCLOG_RESPONSE_STATUS INTEGER,
ACCLOG_TIMESTAMP $TIMESTAMP$,
ACCLOG_PERIOD $TIMESTAMP$,
ACCLOG_PATTERN VARCHAR(128),
ACCLOG_PROJECT VARCHAR(128),
ACCLOG_RESPONSE_TIME INTEGER
)