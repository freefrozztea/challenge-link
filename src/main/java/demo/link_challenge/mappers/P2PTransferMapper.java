package demo.link_challenge.mappers;

import demo.link_challenge.dtos.P2PTransferDTO;
import demo.link_challenge.models.P2PTransfer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = TransactionMapper.class)
public interface P2PTransferMapper {
    P2PTransferDTO toDto(P2PTransfer p2pTransfer);

    @Mapping(target = "status", ignore = true)
    P2PTransfer toEntity(P2PTransferDTO p2pTransferDTO);
}
