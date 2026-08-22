ALTER TABLE school_registration_request
    ADD COLUMN delete_after TIMESTAMPTZ;

UPDATE school_registration_request
SET verification_expires_at = submitted_at + INTERVAL '24 hours',
    delete_after = submitted_at + INTERVAL '24 hours'
WHERE status = 'EMAIL_VERIFICATION_PENDING';

ALTER TABLE school_registration_request
    ADD CONSTRAINT ck_school_registration_verification_deadline CHECK (
        status <> 'EMAIL_VERIFICATION_PENDING'
        OR (
            verification_expires_at IS NOT NULL
            AND delete_after IS NOT NULL
            AND verification_expires_at <= submitted_at + INTERVAL '24 hours'
            AND delete_after <= submitted_at + INTERVAL '24 hours'
        )
    );

CREATE INDEX ix_school_registration_pending_cleanup
    ON school_registration_request (delete_after)
    WHERE status = 'EMAIL_VERIFICATION_PENDING';
