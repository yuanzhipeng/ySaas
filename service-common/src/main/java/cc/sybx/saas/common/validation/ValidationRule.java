package cc.sybx.saas.common.validation;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class ValidationRule {
    protected List<Pattern> whitelistPatterns = new ArrayList();
    protected List<Pattern> blacklistPatterns = new ArrayList();
    private boolean allowNull = false;
    private int minLength = 0;
    private int maxLength = 2147483647;
    private String typeName = null;

    public ValidationRule(String typeName) {
        this.setTypeName(typeName);
    }

    public String getTypeName() {
        return this.typeName;
    }

    public final void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setAllowNull(boolean allowNull) {
        this.allowNull = allowNull;
    }

    public void setMinimumLength(int length) {
        this.minLength = length;
    }

    public void setMaximumLength(int length) {
        this.maxLength = length;
    }

    public void addWhitelistPattern(Pattern p) {
        if (p == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        } else {
            this.whitelistPatterns.add(p);
        }
    }

    public void addBlacklistPattern(Pattern p) {
        if (p == null) {
            throw new IllegalArgumentException("Pattern cannot be null");
        } else {
            this.blacklistPatterns.add(p);
        }
    }

    String checkEmpty(String context, String input) throws ValidationException {
        if (input != null) {
            return input;
        } else if (this.allowNull) {
            return null;
        } else {
            throw new ValidationException(context + ": Input required.");
        }
    }

    String checkLength(String context, String input) throws ValidationException {
        if (input.length() < this.minLength) {
            throw new ValidationException(context + ": Invalid input. The minimum length of " + this.minLength + " characters was not met.");
        } else if (input.length() > this.maxLength) {
            throw new ValidationException(context + ": Invalid input. The maximum length of " + this.maxLength + " characters was exceeded.");
        } else {
            return input;
        }
    }

    String checkWhitelist(String context, String input) throws ValidationException {
        Iterator var3 = this.whitelistPatterns.iterator();

        Pattern p;
        do {
            if (!var3.hasNext()) {
                return input;
            }

            p = (Pattern)var3.next();
        } while(p.matcher(input).matches());

        throw new ValidationException(context + ": Invalid input. Please conform to regex " + p.pattern() + (this.maxLength == 2147483647 ? "" : " with a maximum length of " + this.maxLength));
    }

    String checkBlacklist(String context, String input) throws ValidationException {
        Iterator var3 = this.blacklistPatterns.iterator();

        Pattern p;
        do {
            if (!var3.hasNext()) {
                return input;
            }

            p = (Pattern)var3.next();
        } while(!p.matcher(input).matches());

        throw new ValidationException(context + ": Invalid input. Dangerous input matching " + p.pattern() + " detected.");
    }

    public String getValid(String context, String input) throws ValidationException {
        return this.checkEmpty(context, input) == null ? null : input;
    }
}
