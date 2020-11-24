package linky.command;

public abstract class BasePageSearchCommand extends BasePageCommand {
	protected final String search;

	public BasePageSearchCommand(Integer page, Integer size, String sortField, String sortDirection, String search) {
		super(page, size, sortField, sortDirection);
		this.search = search;
	}

	public String search() {
		return this.search;
	}
}
