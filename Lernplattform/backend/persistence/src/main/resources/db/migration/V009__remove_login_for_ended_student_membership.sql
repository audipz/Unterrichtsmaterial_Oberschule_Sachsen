CREATE OR REPLACE FUNCTION remove_student_login_for_inactive_membership()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
    IF (OLD.status = 'ACTIVE' AND NEW.status <> 'ACTIVE')
       OR (OLD.deleted_at IS NULL AND NEW.deleted_at IS NOT NULL) THEN
        DELETE FROM student_school_login
        WHERE school_membership_id = NEW.id;
    END IF;
    RETURN NEW;
END;
$$;

CREATE TRIGGER trg_remove_student_login_for_inactive_membership
AFTER UPDATE OF status, deleted_at ON school_membership
FOR EACH ROW
EXECUTE FUNCTION remove_student_login_for_inactive_membership();
